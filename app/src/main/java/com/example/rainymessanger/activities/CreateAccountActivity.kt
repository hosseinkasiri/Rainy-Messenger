package com.example.rainymessanger.activities

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.rainymessanger.utils.SingleFragmentActivity
import com.example.rainymessanger.fragments.CreateAccountFragment

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