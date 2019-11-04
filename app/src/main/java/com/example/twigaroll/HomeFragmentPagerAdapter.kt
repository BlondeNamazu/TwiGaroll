package com.example.twigaroll

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomeFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayListOf("Timeline","Gallery")

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getItem(position: Int): Fragment? {
        return when(position){
            0 -> HomeFragment()
            1 -> GalleryFragment()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}