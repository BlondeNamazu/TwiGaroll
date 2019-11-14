package com.example.twigaroll

import androidx.fragment.app.Fragment

interface GalleryNavigator {
    fun addFragmentToActivity(resourceId: Int, fragment: Fragment)
}