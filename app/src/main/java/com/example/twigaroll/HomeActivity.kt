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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupTimeline()
    }

    fun setupTimeline() {
        val session = TwitterCore.getInstance().sessionManager.activeSession ?: return
        val token = session.authToken ?: return

        val userTimeline = UserTimeline.Builder()
            .screenName(session.userName)
            .build()
        val adapter = TweetTimelineListAdapter.Builder(this)
            .setTimeline(userTimeline)
            .build()
        timeline_listview.adapter = adapter
    }
}