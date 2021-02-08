package com.example.rainymessanger.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.rainymessanger.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference

class SettingsActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var mCurrentUser: FirebaseUser
    lateinit var mStorage: StorageReference

    lateinit var mName: TextView
    lateinit var mStatus: TextView
    lateinit var mChangePic: Button
    lateinit var mChangeStatus: Button

    companion object{
        fun newIntent(context: Context) : Intent {
            var intent = Intent(context, SettingsActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        findViews()
        supportActionBar!!.title = "Setting"
        mCurrentUser = FirebaseAuth.getInstance().currentUser!!
        var userId = mCurrentUser.uid
        mDatabase = FirebaseDatabase.getInstance().reference.child("users").child(userId)

        mDatabase.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                var displayName = snapshot.child("displayName").value
                var image = snapshot.child("image").value
                var status = snapshot.child("status").value
                var thumbImage = snapshot.child("thumbImage").value
                mName.text = displayName.toString()
                mStatus.text = status.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun findViews(){
        mName = findViewById(R.id.setting_name)
        mStatus = findViewById(R.id.setting_status)
        mChangePic = findViewById(R.id.setting_change_pic)
        mChangeStatus = findViewById(R.id.setting_change_status)
    }
}