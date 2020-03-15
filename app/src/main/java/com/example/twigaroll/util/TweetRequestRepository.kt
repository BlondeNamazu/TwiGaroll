package com.example.twigaroll.util

import com.twitter.sdk.android.core.models.Tweet

interface TweetRequestRepository {
    suspend fun getTimeLine(): List<Tweet>
    fun postLike(tweetId: Long)
    fun postUnlike(tweetId: Long)
    fun postRetweet(tweetId: Long)
    fun postUnretweet(tweetId: Long)
}