package com.example.taskfour.adapter

class OnboardingAdapter(viewPagerFragment: ViewPagerFragment) : FragmentStateAdapter(viewPagerFragment) {
    private val fragmentList = listOf(
        OnBoardFragmentTwo(),
        OnBoardFragmentOne()
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}