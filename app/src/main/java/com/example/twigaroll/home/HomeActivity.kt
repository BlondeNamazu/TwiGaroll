package com.example.twigaroll.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.twigaroll.*
import com.example.twigaroll.home.gallery.GalleryNavigator
import com.example.twigaroll.home.gallery.GalleryViewModel
import com.example.twigaroll.home.timeline.TimelineViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), GalleryNavigator {

    private lateinit var timelineViewModel: TimelineViewModel
    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewPager.offscreenPageLimit = 2
        viewPager.adapter =
            HomeFragmentPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        timelineViewModel = obtainTimelineViewModel()
        galleryViewModel = obtainGalleryViewModel()
        galleryViewModel.setNavigator(this)
    }

    fun obtainTimelineViewModel(): TimelineViewModel =
        ViewModelProviders.of(this).get(TimelineViewModel::class.java)

    fun obtainGalleryViewModel(): GalleryViewModel =
        ViewModelProviders.of(this).get(GalleryViewModel::class.java)

    override fun addFragmentToActivity(resourceId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(resourceId, fragment)
            .commit()
    }
}