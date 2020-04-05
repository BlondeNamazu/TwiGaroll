package com.example.twigaroll.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.twigaroll.*
import com.example.twigaroll.home.gallery.GalleryNavigator
import com.example.twigaroll.home.gallery.GalleryViewModel
import com.example.twigaroll.ViewModelFactory
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

        val navController = findNavController(R.id.home_container)
        NavigationUI.setupWithNavController(home_bottomnavigation, navController)

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