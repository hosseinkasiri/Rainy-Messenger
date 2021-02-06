package com.example.rainymessanger.controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.rainymessanger.R

class RainyActivity : AppCompatActivity() {

    lateinit var mCreateAccount: Button
    lateinit var mSignIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rainy)
        findViews()
        mCreateAccount.setOnClickListener(View.OnClickListener {
            val intent = CreateAccountActivity.newIntent(this)
            startActivity(intent)
        })

        mSignIn.setOnClickListener(View.OnClickListener {
            val intent = LoginActivity.newIntent(this)
            startActivity(intent)
        })

    }

    private fun findViews() {
        mCreateAccount = findViewById(R.id.create_account)
        mSignIn = findViewById(R.id.have_account)
    }
}