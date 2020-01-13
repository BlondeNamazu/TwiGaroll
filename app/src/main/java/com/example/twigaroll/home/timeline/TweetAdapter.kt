package com.example.twigaroll.home.timeline

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.example.twigaroll.R
import com.example.twigaroll.data.TweetIdData
import com.example.twigaroll.databinding.TweetRowBinding
import com.example.twigaroll.util.FileIO
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

import com.twitter.sdk.android.core.models.Tweet

class TweetAdapter(private val context: Context) :
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
            val binding: TweetRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.tweet_row,
                parent,
                false
            )
            binding.root.tag = binding
            binding.stockButton.setOnClickListener {
                val tweetID = tweetList[position].id
                val json = FileIO.readFile(context)
                val data = if (json.isEmpty()) {
                    TweetIdData(emptyArray())
                } else {
                    converter.fromJson(json) ?: TweetIdData(emptyArray())
                }
                if (data.ids.contains(tweetID)) {
                    Log.d("Namazu", "This tweet has already registered")
                    return@setOnClickListener
                }
                else Log.d("Namazu", "This tweet is new one!")
                val newData = converter
                    .toJson(
                        TweetIdData(
                            data.ids + arrayOf(tweetID)
                        )
                    )
                Log.d("Namazu", newData)
                FileIO.writeFile(context, newData)
            }
            binding
        } else {
            convertView.tag as TweetRowBinding
        }.apply {
            tweet = tweetList[position]
        }
        return binding.root
    }

    fun replaceList(newList: List<Tweet>) {
        tweetList = newList
    }
}