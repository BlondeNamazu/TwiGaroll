package com.example.twigaroll.home.timeline

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.twigaroll.data.TweetIdData
import com.example.twigaroll.databinding.TweetRowBinding
import com.example.twigaroll.util.FileIORepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

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
        }
        return binding.root
    }

    fun replaceList(newList: List<Tweet>) {
        tweetList = newList
    }
}