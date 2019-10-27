package com.example.twigaroll

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_button.callback = object : Callback<TwitterSession>(){
            override fun success(result: Result<TwitterSession>?) {
                Log.d("Namazu","Successfully logged in as ${result?.data?.userName} (id : ${result?.data?.userId})")
                finish()
            }

            override fun failure(exception: TwitterException?) {
                Log.d("Namazu",exception.toString())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        login_button.onActivityResult(requestCode,resultCode,data)
    }
}