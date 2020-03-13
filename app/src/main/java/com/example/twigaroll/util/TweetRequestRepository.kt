package com.example.twigaroll.util

import androidx.lifecycle.LiveData
import com.twitter.sdk.android.core.models.Tweet

interface TweetRequestRepository {
    fun getTimeLine(): LiveData<List<Tweet>>
    fun postLike(tweetId: Long)
    fun postUnlike(tweetId: Long)
    fun postRetweet(tweetId: Long)
    fun postUnretweet(tweetId: Long)
}