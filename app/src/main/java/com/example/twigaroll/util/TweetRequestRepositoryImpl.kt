package com.example.twigaroll.util

import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class TweetRequestRepositoryImpl @Inject constructor() : TweetRequestRepository {
    override suspend fun getTimeLine(): List<Tweet> {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        val statusesService = twitterApiClient.statusesService

        val call = statusesService.homeTimeline(200, null, null, null, null, null, null)

        return call.execute().body()
    }

    override suspend fun postLike(tweetId: Long): Tweet {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        val favoriteService = twitterApiClient.favoriteService
        val call = favoriteService.create(tweetId, true)

        return call.execute().body()
    }

    override suspend fun postUnlike(tweetId: Long): Tweet {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        val favoriteService = twitterApiClient.favoriteService
        val call = favoriteService.destroy(tweetId, true)

        return call.execute().body()
    }

    override suspend fun postRetweet(tweetId: Long): Tweet {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        val statusesService = twitterApiClient.statusesService
        return runBlocking {
            statusesService
                .retweet(tweetId, false)
                .execute()
            statusesService
                .show(tweetId, false, true, true)
                .execute()
                .body()
        }
    }

    override suspend fun postUnretweet(tweetId: Long): Tweet {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        val statusesService = twitterApiClient.statusesService
        return runBlocking {
            statusesService
                .unretweet(tweetId, false)
                .execute()
            statusesService
                .show(tweetId, false, true, true)
                .execute()
                .body()
        }
    }


}