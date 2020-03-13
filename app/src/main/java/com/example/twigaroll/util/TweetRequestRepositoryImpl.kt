package com.example.twigaroll.util

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import javax.inject.Inject

class TweetRequestRepositoryImpl @Inject constructor() : TweetRequestRepository {
    override fun getTimeLine(): LiveData<List<Tweet>> {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        val statusesService = twitterApiClient.statusesService

        val call = statusesService.homeTimeline(200, null, null, null, null, null, null)
        val data = MutableLiveData<List<Tweet>>()

        call.enqueue(object : Callback<List<Tweet>>() {
            override fun success(result: Result<List<Tweet>>?) {
                result ?: return
                data.value = result.data
                Log.d("Namazu", "success to get timeline")
            }

            override fun failure(exception: TwitterException?) {
                Log.d("Namazu", "failed to get timeline")
            }
        })
        return data
    }

    override fun postLike(tweetId: Long) {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        val favoriteService = twitterApiClient.favoriteService
        val call = favoriteService.create(tweetId, false)

        call.enqueue(object : Callback<Tweet>() {
            override fun success(result: Result<Tweet>?) {
                Log.d("Namazu", "success to fav! contents:${result?.data?.text}")
            }

            override fun failure(exception: TwitterException?) {
                Log.d("Namazu", "failed to fav...")
            }
        })
    }

    override fun postUnlike(tweetId: Long) {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        val favoriteService = twitterApiClient.favoriteService
        val call = favoriteService.destroy(tweetId, false)

        call.enqueue(object : Callback<Tweet>() {
            override fun success(result: Result<Tweet>?) {
                Log.d("Namazu", "success to unfav! contents:${result?.data?.text}")
            }

            override fun failure(exception: TwitterException?) {
                Log.d("Namazu", "failed to unfav...")
            }
        })
    }

    override fun postRetweet(tweetId: Long) {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        val statusesService = twitterApiClient.statusesService
        val call = statusesService.retweet(tweetId, false)

        call.enqueue(object : Callback<Tweet>() {
            override fun success(result: Result<Tweet>?) {
                Log.d("Namazu", "success to retweet! contents:${result?.data?.text}")
            }

            override fun failure(exception: TwitterException?) {
                Log.d("Namazu", "failed to retweet...")
            }
        })
    }

    override fun postUnretweet(tweetId: Long) {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        val statusesService = twitterApiClient.statusesService
        val call = statusesService.unretweet(tweetId, false)

        call.enqueue(object : Callback<Tweet>() {
            override fun success(result: Result<Tweet>?) {
                Log.d("Namazu", "success to unretweet! contents:${result?.data?.text}")
            }

            override fun failure(exception: TwitterException?) {
                Log.d("Namazu", "failed to unretweet...")
            }
        })
    }


}