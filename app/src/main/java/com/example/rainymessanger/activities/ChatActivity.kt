package com.example.rainymessanger.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rainymessanger.R

class ChatActivity : AppCompatActivity() {

    companion object{
        val EXT_ID = "userId"
        val EXT_NAME = "name"
        val EXT_STATUS = "status"
        val EXT_PROFILE = "profile"

        fun newIntent(context: Context, userId: String, name: String, status: String, profile: String) : Intent {
            var intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(EXT_ID, userId)
            intent.putExtra(EXT_NAME, name)
            intent.putExtra(EXT_STATUS, status)
            intent.putExtra(EXT_PROFILE, profile)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
    }
}