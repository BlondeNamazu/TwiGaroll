package com.example.twigaroll.home.gallery

import androidx.fragment.app.Fragment

interface GalleryNavigator {
    fun addFragmentToActivity(resourceId: Int, fragment: Fragment)
}