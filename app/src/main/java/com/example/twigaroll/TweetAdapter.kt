package com.example.twigaroll

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.twitter.sdk.android.core.models.Tweet

class TweetAdapter(private val context: Context, private val tweetList: List<Tweet>) :
    BaseAdapter() {
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return tweetList.size
    }

    override fun getItem(position: Int): Any {
        return tweetList[position]
    }

    override fun getItemId(position: Int): Long {
        return tweetList[position].getId()
    }

    override fun getView(position: Int, givenView: View?, parent: ViewGroup): View {
        val convertView = layoutInflater.inflate(R.layout.tweet_row, parent, false)

        val tweet = tweetList[position]

        val screenNameTextView = convertView.findViewById<View>(R.id.screen_name) as TextView
        val tweetTextTextView = convertView.findViewById<View>(R.id.tweet_text) as TextView
        val favoriteCountTextView = convertView.findViewById<View>(R.id.favorite_count) as TextView

        screenNameTextView.text = tweet.user.name
        tweetTextTextView.text = tweet.text
        favoriteCountTextView.text = tweet.favoriteCount.toString()

        return convertView
    }
}