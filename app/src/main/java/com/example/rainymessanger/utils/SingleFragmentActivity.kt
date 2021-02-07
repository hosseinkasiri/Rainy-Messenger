package com.example.rainymessanger.utils

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.rainymessanger.R

open abstract class SingleFragmentActivity : AppCompatActivity() {

    abstract fun mFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null) {
            var manager = supportFragmentManager.beginTransaction()
            manager.replace(R.id.activity_login, mFragment())
            manager.commitAllowingStateLoss()
        }
    }
}