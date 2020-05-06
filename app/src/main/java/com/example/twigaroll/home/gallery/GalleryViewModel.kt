package com.example.twigaroll.home.gallery

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twigaroll.home.gallery_detail.GalleryDetailFragment
import com.example.twigaroll.R
import com.example.twigaroll.data.TweetIdData
import com.example.twigaroll.util.FileIORepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val fileIORepository: FileIORepository
) : ViewModel() {

    private val _imageURLs = MutableLiveData<List<String>>()
    val imageURLs: LiveData<List<String>>
        get() = _imageURLs
    private lateinit var navigator: GalleryNavigator
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val converter: JsonAdapter<TweetIdData> = moshi.adapter(TweetIdData::class.java)

    fun refreshTimeline(v: View) {
        val json = fileIORepository.readFile(v.context)
        val data = if (json.isEmpty()) {
            TweetIdData(emptyArray())
        } else {
            converter.fromJson(json) ?: TweetIdData(emptyArray())
        }
        _imageURLs.value = data.mediaURLs.toList()
    }

    fun setNavigator(navigator: GalleryNavigator) {
        this.navigator = navigator
    }

    fun beginGalleryDetailFragment(position: Int) {
        if (!this::navigator.isInitialized) return
        val bundle = Bundle()
        bundle.putString(
            "imageURL",
            imageURLs.value?.get(position)
                ?: return
        )
        val detailFragment = GalleryDetailFragment()
        detailFragment.arguments = bundle
        navigator.addFragmentToActivity(R.id.gallery_container, detailFragment)
    }
}