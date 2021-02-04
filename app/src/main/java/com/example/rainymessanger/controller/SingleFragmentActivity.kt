package com.example.rainymessanger.controller

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.rainymessanger.R

open abstract class SingleFragmentActivity : AppCompatActivity() {

    abstract fun mFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_login)
        var manager: FragmentManager = supportFragmentManager
        if (manager.findFragmentById(R.id.activity_login) == null){
            manager.beginTransaction()
                .add(R.id.activity_login, mFragment())
                .commit();
        }
    }
}