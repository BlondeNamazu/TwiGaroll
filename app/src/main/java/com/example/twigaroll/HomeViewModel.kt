package com.example.twigaroll

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet

class HomeViewModel : ViewModel() {

    private val _tweetList = MutableLiveData<List<Tweet>>()
    val tweetList: LiveData<List<Tweet>>
        get() = _tweetList

    fun refreshTimeline(v: View) {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        val statusesService = twitterApiClient.statusesService

        val call = statusesService.homeTimeline(20, null, null, null, null, null, null)

        call.enqueue(object : Callback<List<Tweet>>() {
            override fun success(result: Result<List<Tweet>>?) {
                result ?: return
                _tweetList.value = result.data
                Log.d("Namazu", "success to get timeline")
            }

            override fun failure(exception: TwitterException?) {
                Log.d("Namazu", "failed to get timeline")
            }
        })

    }
}