package com.example.twigaroll

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.twitter.sdk.android.core.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(TwitterCore.getInstance().sessionManager.activeSession == null){
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }else{
            Log.d("Namazu","Already logged in as ${TwitterCore.getInstance().sessionManager.activeSession.userName} (id : ${TwitterCore.getInstance().sessionManager.activeSession.userId})")
        }

        go_to_tweet_home.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }

    }
}
