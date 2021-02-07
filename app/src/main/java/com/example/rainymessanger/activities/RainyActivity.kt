package com.example.rainymessanger.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.rainymessanger.R
import com.example.rainymessanger.helper.Toaster
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RainyActivity : AppCompatActivity() {

    var mCreateAccount: Button? = null
    var mSignIn: Button? = null
    var mAuth: FirebaseAuth? = null
    var mUser: FirebaseUser? = null
    var mAuthListener: FirebaseAuth.AuthStateListener? = null

    companion object{
        fun newIntent(context: Context) : Intent {
            var intent = Intent(context, RainyActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rainy)
        findViews()

        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener {
            firebaseAuth: FirebaseAuth ->
            mUser = firebaseAuth.currentUser
            if (mUser != null) {
                val userName = mUser!!.email!!.split("@")[0]
                val intent = DashboardActivity.newIntent(this, userName)
                startActivity(intent)
                finish()
            }else
                Toaster.makeToast(this, "not logged in")
        }
        mCreateAccount!!.setOnClickListener(View.OnClickListener {
            val intent = CreateAccountActivity.newIntent(this)
            startActivity(intent)
        })

        mSignIn!!.setOnClickListener(View.OnClickListener {
            val intent = LoginActivity.newIntent(this)
            startActivity(intent)
        })
    }

    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener(mAuthListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (mAuth != null){
            mAuth!!.removeAuthStateListener(mAuthListener!!)
        }
    }

    private fun findViews() {
        mCreateAccount = findViewById(R.id.create_account)
        mSignIn = findViewById(R.id.have_account)
    }
}