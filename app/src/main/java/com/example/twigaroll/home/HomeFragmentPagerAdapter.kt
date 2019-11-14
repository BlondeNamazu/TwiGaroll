package com.example.twigaroll.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.twigaroll.home.gallery.GalleryFragment
import com.example.twigaroll.home.timeline.TimelineFragment

class HomeFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val tabTitles = arrayListOf("Timeline","Gallery")

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getItem(position: Int): Fragment? {
        return when(position){
            0 -> TimelineFragment()
            1 -> GalleryFragment()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}