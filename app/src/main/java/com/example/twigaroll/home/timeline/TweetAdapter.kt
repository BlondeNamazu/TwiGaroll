package com.example.twigaroll.home.timeline

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.twigaroll.data.TweetIdData
import com.example.twigaroll.databinding.TweetRowBinding
import com.example.twigaroll.util.FileIORepository
import com.example.twigaroll.util.TweetRequestRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

import com.twitter.sdk.android.core.models.Tweet
import kotlinx.coroutines.*
import javax.inject.Inject

class TweetAdapter @Inject constructor(
    private val fileIORepository: FileIORepository,
    private val tweetRequestRepository: TweetRequestRepository
) :
    BaseAdapter() {
    var tweetList = emptyList<Tweet>().toMutableList()
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
            stockButton.setOnClickListener {
                val json = fileIORepository.readFile(parent.context)
                val data = if (json.isEmpty()) {
                    TweetIdData(emptyArray())
                } else {
                    converter.fromJson(json) ?: TweetIdData(emptyArray())
                }
                tweet!!.entities.media.forEach { media ->
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
                val thisTweet = tweet ?: return@setOnClickListener

                if (thisTweet.favorited) {
                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] = tweetRequestRepository.postUnlike(thisTweet.id)
                        }
                        withContext(Dispatchers.Main) {
                            notifyDataSetChanged()
                        }
                    }
                } else {
                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] = tweetRequestRepository.postLike(thisTweet.id)
                        }
                        withContext(Dispatchers.Main) {
                            notifyDataSetChanged()
                        }
                    }
                }

            }
            retweetButton.setOnClickListener {
                val thisTweet = tweet ?: return@setOnClickListener

                if (thisTweet.retweeted) {
                    Log.d("Namazu", "retweet -> unretweet")
                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] = tweetRequestRepository.postUnretweet(thisTweet.id)
                            Log.d("Namazu", "retweeted : ${tweetList[position].retweeted}")
                            Log.d("Namazu", "tweetId : ${tweetList[position].id}")
                        }
                        withContext(Dispatchers.Main) {
                            notifyDataSetChanged()
                        }
                    }
                } else {
                    Log.d("Namazu", "unretweet -> retweet")
                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] = tweetRequestRepository.postRetweet(thisTweet.id)
                            Log.d("Namazu", "retweeted : ${tweetList[position].retweeted}")
                            Log.d("Namazu", "tweetId : ${tweetList[position].id}")
                        }
                        withContext(Dispatchers.Main) {
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        }
        return binding.root
    }

    fun replaceList(newList: List<Tweet>) {
        tweetList = newList.toMutableList()
    }

}