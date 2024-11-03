package com.example.taskfour.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.taskfour.view.OnBoardOFragment
import com.example.taskfour.view.OnBoardTFragment
import com.example.taskfour.view.ViewPagerFragment

class OnboardingAdapter(viewPagerFragment: ViewPagerFragment) : FragmentStateAdapter(viewPagerFragment) {
    private val fragmentList = listOf(
        OnBoardOFragment(),
        OnBoardTFragment()
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}