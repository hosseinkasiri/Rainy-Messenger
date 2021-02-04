package com.example.rainymessanger.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class CreateAccountActivity : SingleFragmentActivity() {

    override fun mFragment(): Fragment {
        return CreateAccountFragment.newInstance()
    }
}