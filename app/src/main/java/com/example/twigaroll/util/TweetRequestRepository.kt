package com.example.twigaroll.util

import com.twitter.sdk.android.core.models.Tweet

interface TweetRequestRepository {
    suspend fun getTimeLine(): List<Tweet>
    suspend fun postLike(tweetId: Long): Tweet
    suspend fun postUnlike(tweetId: Long): Tweet
    suspend fun postRetweet(tweetId: Long): Tweet
    suspend fun postUnretweet(tweetId: Long): Tweet
}