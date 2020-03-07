package com.example.twigaroll.home.timeline

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.example.twigaroll.data.TweetIdData
import com.example.twigaroll.databinding.TweetRowBinding
import com.example.twigaroll.util.FileIORepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException

import com.twitter.sdk.android.core.models.Tweet
import javax.inject.Inject

class TweetAdapter @Inject constructor(
    private val fileIORepository: FileIORepository
) :
    BaseAdapter() {
    private var tweetList = emptyList<Tweet>()
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val converter: JsonAdapter<TweetIdData> = moshi.adapter(TweetIdData::class.java)

    override fun getCount(): Int {
        return tweetList.size
    }

    override fun getItem(position: Int): Any {
        return tweetList[position]
    }

    override fun getItemId(position: Int): Long {
        return tweetList[position].id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = TweetRowBinding.inflate(inflater, parent, false)
            binding.root.tag = binding
            binding
        } else {
            convertView.tag as TweetRowBinding
        }.apply {
            tweet = tweetList[position]
            favCount =
                tweetList[position].favoriteCount - if (tweetList[position].favorited) 1 else 0
            favorited = tweetList[position].favorited
            retCount =
                tweetList[position].retweetCount - if (tweetList[position].retweeted) 1 else 0
            retweeted = tweetList[position].retweeted
            stockButton.setOnClickListener {
                val json = fileIORepository.readFile(parent.context)
                val data = if (json.isEmpty()) {
                    TweetIdData(emptyArray())
                } else {
                    converter.fromJson(json) ?: TweetIdData(emptyArray())
                }
                tweetList[position].entities.media.forEach { media ->
                    val url = media.mediaUrlHttps
                    if (data.mediaURLs.contains(url)) {
                        Log.d("Namazu", "This tweet has already registered")
                        return@setOnClickListener
                    } else Log.d("Namazu", "This tweet is new one!")
                    val newData = converter
                        .toJson(
                            TweetIdData(
                                arrayOf(url) + data.mediaURLs
                            )
                        )
                    Log.d("Namazu", newData)
                    fileIORepository.writeFile(parent.context, newData)
                }
            }
            favButton.setOnClickListener {
                val twitterApiClient = TwitterCore.getInstance().apiClient
                val statusesService = twitterApiClient.favoriteService

                if (favorited) {
                    val call = statusesService.destroy(tweetList[position].id, false)

                    call.enqueue(object : Callback<Tweet>() {
                        override fun success(result: Result<Tweet>?) {
                            Log.d("Namazu", "success to unfav! contents:${result?.data?.text}")
                            Toast.makeText(parent.context, "Unfav succeed!", Toast.LENGTH_LONG)
                                .show()
                            favorited = false
                        }

                        override fun failure(exception: TwitterException?) {
                            Log.d("Namazu", "failed to unfav...")
                            Toast.makeText(
                                parent.context,
                                "Unfav failed: ${exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
                } else {
                    val call = statusesService.create(tweetList[position].id, false)

                    call.enqueue(object : Callback<Tweet>() {
                        override fun success(result: Result<Tweet>?) {
                            Log.d("Namazu", "success to fav! contents:${result?.data?.text}")
                            Toast.makeText(parent.context, "Fav succeed!", Toast.LENGTH_LONG).show()
                            favorited = true
                        }

                        override fun failure(exception: TwitterException?) {
                            Log.d("Namazu", "failed to fav...")
                            Toast.makeText(
                                parent.context,
                                "Fav failed: ${exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
                }

            }
            retweetButton.setOnClickListener {
                val twitterApiClient = TwitterCore.getInstance().apiClient
                val statusesService = twitterApiClient.statusesService

                if (retweeted) {
                    val call = statusesService.unretweet(tweetList[position].id, false)

                    call.enqueue(object : Callback<Tweet>() {
                        override fun success(result: Result<Tweet>?) {
                            Log.d("Namazu", "success to unretweet! contents:${result?.data?.text}")
                            Toast.makeText(parent.context, "Unretweet succeed!", Toast.LENGTH_LONG)
                                .show()
                            retweeted = false
                        }

                        override fun failure(exception: TwitterException?) {
                            Log.d("Namazu", "failed to unretweet...")
                            Toast.makeText(
                                parent.context,
                                "Unretweet failed: ${exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
                } else {
                    val call = statusesService.retweet(tweetList[position].id,false)

                    call.enqueue(object : Callback<Tweet>() {
                        override fun success(result: Result<Tweet>?) {
                            Log.d("Namazu", "success to ret! contents:${result?.data?.text}")
                            Toast.makeText(parent.context, "Retweet succeed!", Toast.LENGTH_LONG).show()
                            retweeted = true
                        }

                        override fun failure(exception: TwitterException?) {
                            Log.d("Namazu", "failed to retweet...")
                            Toast.makeText(
                                parent.context,
                                "Retweet failed: ${exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
                }
            }
        }
        return binding.root
    }

    fun replaceList(newList: List<Tweet>) {
        tweetList = newList
    }
}