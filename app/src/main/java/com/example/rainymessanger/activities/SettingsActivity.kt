package com.example.rainymessanger.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rainymessanger.R

class SettingsActivity : AppCompatActivity() {

    companion object{
        fun newIntent(context: Context) : Intent {
            var intent = Intent(context, SettingsActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}