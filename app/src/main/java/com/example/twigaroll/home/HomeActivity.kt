package com.example.twigaroll.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.twigaroll.*
import com.example.twigaroll.home.gallery.GalleryNavigator
import com.example.twigaroll.home.gallery.GalleryViewModel
import com.example.twigaroll.ViewModelFactory
import com.example.twigaroll.home.gallery.GalleryFragment
import com.example.twigaroll.home.timeline.TimelineFragment
import com.example.twigaroll.home.timeline.TimelineViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), GalleryNavigator {

    private lateinit var timelineViewModel: TimelineViewModel
    private lateinit var galleryViewModel: GalleryViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val WRITE_REQUEST_CODE = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        home_bottomnavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_timeline_item -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.home_container, TimelineFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_gallery_item -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.home_container, GalleryFragment())
                        .commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
        home_bottomnavigation.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.navigation_timeline_item -> {
                    timelineViewModel.toggleShouldBackToTimelineTop(true)
                }
                R.id.navigation_gallery_item -> {
                }
            }
        }
        addFragmentToActivity(R.id.home_container, TimelineFragment())

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_REQUEST_CODE)

    }

    fun obtainTimelineViewModel(): TimelineViewModel =
        ViewModelProviders.of(this, viewModelFactory).get(TimelineViewModel::class.java)

    fun obtainGalleryViewModel(): GalleryViewModel =
        ViewModelProviders.of(this, viewModelFactory).get(GalleryViewModel::class.java)

    override fun addFragmentToActivity(resourceId: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .add(resourceId, fragment)
            .commit()
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                permission
            )
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission), requestCode
            )
        } else {
            Toast.makeText(this, "Permission needed", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission), requestCode
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            WRITE_REQUEST_CODE -> {
                if (grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Namazu", "permission granted")
                    timelineViewModel = obtainTimelineViewModel()
                    timelineViewModel.setNavigator(this)
                    galleryViewModel = obtainGalleryViewModel()
                    galleryViewModel.setNavigator(this)

                } else {
                    Log.d("Namazu", "permission refused")
                }
            }
        }
    }
}