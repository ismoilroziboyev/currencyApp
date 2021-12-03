package uz.ismoilroziboyev.valute.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.ismoilroziboyev.valute.fragments.AllValutesFragment
import uz.ismoilroziboyev.valute.fragments.CalculatoFragment
import uz.ismoilroziboyev.valute.fragments.MainPageFragment

class MainViewPagerAdapter(fragment: Fragment, val openCalcPage: AllValutesFragment.OpenCalcPage) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3


    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MainPageFragment()
            1 -> AllValutesFragment(openCalcPage)
            else -> CalculatoFragment()
        }
    }
}