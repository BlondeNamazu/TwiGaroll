package com.example.twigaroll

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.twitter.sdk.android.core.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        if (TwitterCore.getInstance().sessionManager.activeSession == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
