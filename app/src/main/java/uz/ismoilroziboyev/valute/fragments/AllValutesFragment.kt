package uz.ismoilroziboyev.valute.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import uz.ismoilroziboyev.valute.R
import uz.ismoilroziboyev.valute.adapters.AllValutesRvAdapter
import uz.ismoilroziboyev.valute.databinding.FragmentAllValutesBinding
import uz.ismoilroziboyev.valute.models.ValuteModel
import uz.ismoilroziboyev.valute.utils.MySharedPreferences
import uz.ismoilroziboyev.valute.viewModel.MyViewModel

class AllValutesFragment(val openCalcPage: OpenCalcPage) : Fragment() {


    private lateinit var binding: FragmentAllValutesBinding
    private lateinit var myViewModel: MyViewModel
    private lateinit var allValutesRvAdapter: AllValutesRvAdapter
    private lateinit var list: ArrayList<ValuteModel>
    private lateinit var mySharedPreferences: MySharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllValutesBinding.inflate(inflater, container, false)

        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]
        list = ArrayList()
        mySharedPreferences = MySharedPreferences.getInstance(requireContext())

        allValutesRvAdapter = AllValutesRvAdapter(list = list, onItemClickListener =
        object : AllValutesRvAdapter.OnItemClickListener {
            override fun onItemClickListener(item: ValuteModel) {
                mySharedPreferences.putNewCalcCurrency(item.code)
                openCalcPage.openCalcPage()
                val snackbar = Snackbar.make(
                    binding.recyclerView,
                    "Yangi valyuta turi kalkulatorga o'rnatildi!",
                    BaseTransientBottomBar.LENGTH_SHORT
                ).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)

                snackbar.setAction("Ok") { snackbar.dismiss() }
                snackbar.setActionTextColor(Color.parseColor("#3D7992"))

                snackbar.show()

            }
        })

        binding.apply {

            recyclerView.adapter = allValutesRvAdapter
            myViewModel.getValutesList(requireContext()).observe(viewLifecycleOwner, Observer {
                list.addAll(it)
                allValutesRvAdapter.notifyDataSetChanged()

            })
        }

        return binding.root
    }

    interface OpenCalcPage {
        fun openCalcPage()
    }

}