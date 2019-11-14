package com.example.twigaroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), GalleryNavigator {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewPager.offscreenPageLimit = 2
        viewPager.adapter = HomeFragmentPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

        homeViewModel = obtainHomeViewModel()
        galleryViewModel = obtainGalleryViewModel()
        galleryViewModel.setNavigator(this)
    }

    fun obtainHomeViewModel(): HomeViewModel =
        ViewModelProviders.of(this).get(HomeViewModel::class.java)

    fun obtainGalleryViewModel(): GalleryViewModel =
        ViewModelProviders.of(this).get(GalleryViewModel::class.java)

    override fun addFragmentToActivity(resourceId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(resourceId, fragment)
            .commit()
    }
}