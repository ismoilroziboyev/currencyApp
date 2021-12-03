package uz.ismoilroziboyev.valute.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.viewpager2.widget.ViewPager2
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uz.ismoilroziboyev.valute.adapters.MiniVPAdapter
import uz.ismoilroziboyev.valute.R
import uz.ismoilroziboyev.valute.adapters.ChangesHistoryRvAdapter
import uz.ismoilroziboyev.valute.appdatabase.AppDatabase
import uz.ismoilroziboyev.valute.databinding.FragmentMainPageBinding
import uz.ismoilroziboyev.valute.databinding.ItemTabLayoutBinding
import uz.ismoilroziboyev.valute.models.OneHistoryModel
import uz.ismoilroziboyev.valute.models.ValuteModel
import uz.ismoilroziboyev.valute.utils.MySharedPreferences
import uz.ismoilroziboyev.valute.viewModel.MyViewModel
import uz.ismoilroziboyev.valute.work.MyWorker
import uz.mobiler.workmanager.utils.NetworkHelper
import java.util.concurrent.TimeUnit

class MainPageFragment : Fragment() {


    private lateinit var binding: FragmentMainPageBinding
    private lateinit var myViewModel: MyViewModel
    private lateinit var miniViewPagerAdapter: MiniVPAdapter
    private lateinit var list: ArrayList<ValuteModel>
    private lateinit var changesHistoryRvAdapter: ChangesHistoryRvAdapter
    private lateinit var appDatabase: AppDatabase
    private lateinit var workManager: WorkManager
    private lateinit var handler: Handler
    private lateinit var mySharedPreferences: MySharedPreferences
    private lateinit var networkHelper: NetworkHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPageBinding.inflate(layoutInflater, container, false)

        appDatabase = AppDatabase.getInstance(requireContext())

        workManager = WorkManager.getInstance(requireContext())
        startWork()

        handler = Handler(Looper.getMainLooper())

        mySharedPreferences = MySharedPreferences.getInstance(requireContext())

        setCalcDefaultCurrency()

        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]

        networkHelper = NetworkHelper(requireContext())

        binding.apply {

            list = ArrayList()

            miniViewPagerAdapter = MiniVPAdapter(list)

            myViewModel.getValutesList(requireContext())
                .observe(viewLifecycleOwner, Observer {
                    list.addAll(it.filter { valute -> valute.nbu_buy_price != "" }.reversed())

                    addToHistoryNewValutes(it)

                    miniViewPagerAdapter.notifyDataSetChanged()

                    val mainPageList: List<ValuteModel> = loadMainPageList(list[0].code)

                    if (mainPageList.isEmpty()) {
                        recyclerView.visibility = View.GONE
                        emptyTv.visibility = View.VISIBLE
                    } else {
                        changesHistoryRvAdapter = ChangesHistoryRvAdapter(mainPageList)
                        recyclerView.adapter = changesHistoryRvAdapter
                        val itemDecoration = DividerItemDecoration(context, 1)

                        recyclerView.addItemDecoration(itemDecoration)
                    }

                    TabLayoutMediator(
                        viewpagertab,
                        viewPager
                    )
                    { tab, position ->
                        val itemTabLayoutBinding = ItemTabLayoutBinding.inflate(inflater)
                        itemTabLayoutBinding.item = list[position]

                        if (position != 0) {
                            itemTabLayoutBinding.root.background = null
                            itemTabLayoutBinding.tv.setTextColor(Color.parseColor("#D0D0D0"))
                        }

                        tab.customView = itemTabLayoutBinding.root

                    }.attach()

                    springDotsIndicator.setViewPager2(viewPager)

                })
            viewPager.adapter = miniViewPagerAdapter

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    changesHistoryRvAdapter.list = loadMainPageList(list[position].code)
                    changesHistoryRvAdapter.notifyDataSetChanged()
                }
            })



            viewpagertab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val tablayoutBinding =
                        DataBindingUtil.getBinding<ItemTabLayoutBinding>(tab?.customView!!)

                    tablayoutBinding?.root?.background = resources.getDrawable(R.drawable.tab_back)
                    tablayoutBinding?.tv?.setTextColor(Color.WHITE)

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                    val tablayoutBinding =
                        DataBindingUtil.getBinding<ItemTabLayoutBinding>(tab?.customView!!)

                    tablayoutBinding?.root?.background = null
                    tablayoutBinding?.tv?.setTextColor(Color.parseColor("#D0D0D0"))
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })

        }





        return binding.root
    }

    private fun setCalcDefaultCurrency() {
        if (mySharedPreferences.getCurrentCalcCurrency() == "") {
            mySharedPreferences.putNewCalcCurrency("USD")
        }
    }

    private fun startWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .build()

        val workRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 15L, TimeUnit.MINUTES)
            .setConstraints(constraints).build()

        workManager.enqueue(workRequest)
    }

    private fun loadMainPageList(code: String): List<ValuteModel> {
        val mainPagesList = ArrayList<ValuteModel>()

        appDatabase.valuteModelDao().getAllHistoriesList().forEach {
            val index = it.list.indexOf(ValuteModel("", code, "", "", "", ""))
            mainPagesList.add(it.list[index])
        }

        return mainPagesList.reversed()

    }

    private fun addToHistoryNewValutes(it: List<ValuteModel>) {
        if (appDatabase.valuteModelDao().getAllHistoriesList().isNotEmpty()) {
            if (appDatabase.valuteModelDao().getAllHistoriesList()
                    .reversed()[0].list[0].date != it[0].date
            ) {
                val oneHistoryModel = OneHistoryModel(list = it)
                appDatabase.valuteModelDao().putNewOneHistory(oneHistoryModel)
            }
        } else {
            val oneHistoryModel = OneHistoryModel(list = it)
            appDatabase.valuteModelDao().putNewOneHistory(oneHistoryModel)
        }
    }

}