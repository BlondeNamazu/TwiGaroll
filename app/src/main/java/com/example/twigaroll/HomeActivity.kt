package com.example.twigaroll

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.twitter.sdk.android.tweetui.UserTimeline
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var adapter : TweetAdapter
    private var tweetList = emptyList<Tweet>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        adapter = TweetAdapter(this, tweetList)
        timeline_listview.adapter = adapter
        getHomeTimeline()
        get_timeline.setOnClickListener { getHomeTimeline() }
    }

    fun getHomeTimeline() {
        val twitterApiClient = TwitterCore.getInstance().apiClient
        val statusesService = twitterApiClient.statusesService

        val call = statusesService.homeTimeline(20,null,null,null,null,null,null)

        call.enqueue(object : Callback<List<Tweet>>(){
            override fun success(result: Result<List<Tweet>>?) {
                result ?: return
                tweetList.removeAll(tweetList)
                tweetList.addAll(result.data)
                adapter.notifyDataSetChanged()
                Log.d("Namazu","success to get timeline")
            }

            override fun failure(exception: TwitterException?) {
                Log.d("Namazu","failed to get timeline")
            }
        })
    }
}