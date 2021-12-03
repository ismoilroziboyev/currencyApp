package uz.ismoilroziboyev.valute.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.widget.SearchView
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import uz.ismoilroziboyev.valute.R
import uz.ismoilroziboyev.valute.adapters.MainViewPagerAdapter
import uz.ismoilroziboyev.valute.databinding.ActivityMainBinding
import uz.ismoilroziboyev.valute.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainViewPagerAdapter: MainViewPagerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        mainViewPagerAdapter = MainViewPagerAdapter(this, object : AllValutesFragment.OpenCalcPage {
            override fun openCalcPage() {
                binding.viewPager.currentItem = 2
            }
        })





        binding.apply {
            viewPager.adapter = mainViewPagerAdapter
            viewPager.isUserInputEnabled = false

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> bottomNavigationView.selectedItemId = R.id.home_page
                        1 -> bottomNavigationView.selectedItemId = R.id.all_valutes_page
                        2 -> bottomNavigationView.selectedItemId = R.id.calculator_page
                    }

                }
            })

            bottomNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home_page -> viewPager.currentItem = 0
                    R.id.all_valutes_page -> viewPager.currentItem = 1
                    R.id.calculator_page -> viewPager.currentItem = 2
                }

                return@setOnItemSelectedListener true
            }

        }

        return binding.root
    }


}