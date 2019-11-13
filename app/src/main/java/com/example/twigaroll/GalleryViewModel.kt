package com.example.twigaroll

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet

class GalleryViewModel : ViewModel() {

    private val _imageURLs = MutableLiveData<List<String>>()
    val imageURLs: LiveData<List<String>>
        get() = _imageURLs

    fun refreshTimeline(v: View) {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        val statusesService = twitterApiClient.statusesService

        val call = statusesService.homeTimeline(200, null, null, null, null, null, null)

        call.enqueue(object : Callback<List<Tweet>>() {
            override fun success(result: Result<List<Tweet>>?) {
                result ?: return
                _imageURLs.value = result.data.filter {
                    it.extendedEntities.media.isNotEmpty()
                }.flatMap {
                    it.extendedEntities.media
                }.map {
                    it.mediaUrlHttps
                }
                Log.d("Namazu", "success to get timeline")
            }

            override fun failure(exception: TwitterException?) {
                Log.d("Namazu", "failed to get timeline : ${exception.toString()}")
            }
        })

    }

    fun beginGalleryDetailFragment(fragmentManager: FragmentManager, position: Int) {
        val bundle = Bundle()
        bundle.putString(
            "imageURL",
            imageURLs.value?.get(position)
                ?: return
        )
        val detailFragment = GalleryDetailFragment()
        detailFragment.arguments = bundle
        fragmentManager.beginTransaction()
            .add(R.id.gallery_container, detailFragment)
            .commit()
    }
}