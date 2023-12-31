package com.example.taskmanager.presentation.MainFragment

import androidx.fragment.app.Fragment
import com.example.taskmanager.presentation.ListFragment.ListFragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return ListFragment.newInstance(position)
    }
}