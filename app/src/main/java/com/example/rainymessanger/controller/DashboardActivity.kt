package com.example.rainymessanger.controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class DashboardActivity : SingleFragmentActivity() {

    companion object{
        val EXTRA_NAME = "name"

        fun newIntent(context: Context, name: String): Intent{
            var intent = Intent(context, DashboardActivity::class.java)
            intent.putExtra(EXTRA_NAME, name)
            return intent
        }
    }

    override fun mFragment(): Fragment {
        return DashboardFragment.newInstance()
    }
}