package com.example.twigaroll.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.twigaroll.R
import com.squareup.picasso.Picasso
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
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

    override suspend fun postTweet(text: String): Tweet {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        val statusesService = twitterApiClient.statusesService
        return runBlocking {
            statusesService
                .update(text, null, null, null, null, null, null, false, null)
                .execute()
                .body()
        }
    }

    override suspend fun postStamp(context: Context, inReplyToStatusId: Long, imageURL: String) {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        runBlocking {
            val bmp = Picasso.get().load(imageURL).get() ?: return@runBlocking
            val stream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val bytes = stream.toByteArray()
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), bytes)
            val uploadedMediaIdStr = twitterApiClient
                .mediaService
                .upload(requestBody, null, null)
                .execute()
                .body()
                .mediaIdString ?: return@runBlocking
            twitterApiClient
                .statusesService
                .update(
                    "",
                    inReplyToStatusId,
                    null,
                    null,
                    null,
                    null,
                    null,
                    false,
                    uploadedMediaIdStr
                )
                .enqueue(object : Callback<Tweet>() {
                    override fun success(result: Result<Tweet>?) {
                        println("success! id: ${result?.data?.id}")
                    }

                    override fun failure(exception: TwitterException?) {
                        println("failure due to ${exception.toString()}")
                    }
                })
        }
    }
}