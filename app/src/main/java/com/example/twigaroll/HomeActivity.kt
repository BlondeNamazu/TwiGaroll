package com.example.twigaroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.content_frame, findOrCreateViewFragment())
        }.commit()

        galleryViewModel = obtainGalleryViewModel()
    }

    private fun findOrCreateViewFragment() =
        supportFragmentManager.findFragmentById(R.id.content_frame) ?: GalleryFragment()

    fun obtainHomeViewModel(): HomeViewModel =
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    fun obtainGalleryViewModel(): GalleryViewModel =
        ViewModelProviders.of(this).get(GalleryViewModel::class.java)
}