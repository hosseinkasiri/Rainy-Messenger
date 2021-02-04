package com.example.rainymessanger.controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.rainymessanger.R

class LoginActivity : SingleFragmentActivity() {

    fun newIntent(context: Context) : Intent{
        var intent = Intent(context, LoginActivity::class.java)
        return intent
    }

    override fun mFragment(): Fragment {
        return LoginFragment.newInstance()
    }
}