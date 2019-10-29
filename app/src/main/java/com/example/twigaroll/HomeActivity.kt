package com.example.twigaroll

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.content_frame, findOrCreateViewFragment())
        }.commit()

        homeViewModel = obtainViewModel()
    }

    private fun findOrCreateViewFragment() =
        supportFragmentManager.findFragmentById(R.id.content_frame) ?: HomeFragment()

    fun obtainViewModel(): HomeViewModel =
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
}