package com.example.rainymessanger.controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.rainymessanger.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateAccountActivity : SingleFragmentActivity() {

    companion object{
        fun newIntent(context: Context) : Intent {
            var intent = Intent(context, CreateAccountActivity::class.java)
            return intent
        }
    }

    override fun mFragment(): Fragment {
        return CreateAccountFragment.newInstance()
    }
}