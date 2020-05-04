package com.example.twigaroll.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.example.twigaroll.R
import com.example.twigaroll.home.gallery.GalleryFragment
import com.example.twigaroll.home.timeline.TimelineFragment

@Navigator.Name("twigaroll_fragment")
class TwiGarollNaivgator(
    private val context: Context,
    private val manager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {
    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        val tag = destination.id
        val transaction = manager.beginTransaction()

        val currentFragment = manager.primaryNavigationFragment
        if (currentFragment != null) {
            transaction.detach(currentFragment)
        }

        var fragment = manager.findFragmentByTag(tag.toString())
        if (fragment == null) {
            fragment = when (tag) {
                R.id.navigation_timeline_item -> TimelineFragment()
                R.id.navigation_gallery_item -> GalleryFragment()
                else -> {
                    Log.d("Namazu", "tag is ${tag}")
                    GalleryFragment()
                }
            }
            transaction.add(containerId, fragment, tag.toString())
        } else {
            transaction.attach(fragment)
        }

        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setReorderingAllowed(true)
        transaction.commit()

        return destination
    }
}