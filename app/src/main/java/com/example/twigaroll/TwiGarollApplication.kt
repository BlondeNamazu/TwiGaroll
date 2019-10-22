package com.example.twigaroll

import android.app.Application
import android.util.Log
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig

class TwiGarollApplication : Application(){

    private val CONSUMER_KEY = "7kk6FGu7YjK4oV5JPzAci9vzG"
    private val CONSUMER_API_KEY = "KfY597zwd7vmCvzmb4mjykitCz0mGNAiCR3JfHxjiuG8KzpqYU"

    override fun onCreate() {
        super.onCreate()

        val config = TwitterConfig.Builder(this)
            .logger(DefaultLogger(Log.DEBUG))
            .twitterAuthConfig(TwitterAuthConfig(CONSUMER_KEY,CONSUMER_API_KEY))
            .debug(true)
            .build()
        Twitter.initialize(config)
    }
}