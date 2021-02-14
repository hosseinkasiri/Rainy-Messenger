package com.example.rainymessanger.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.rainymessanger.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class ProfileActivity : AppCompatActivity() {

    lateinit var mCurrentUser: FirebaseUser
    lateinit var mUserDatabase: DatabaseReference
    lateinit var mUserId: String

    lateinit var mProfile: ImageView
    lateinit var mName: TextView
    lateinit var mStatus: TextView

    companion object{
        val EXT_ID = "userId"
        fun newIntent(context: Context, userId: String) : Intent {
            var intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra(EXT_ID, userId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        findViews()
        supportActionBar!!.title = "Profile"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (intent.extras != null){
            mUserId = intent.extras!!.getString(EXT_ID).toString()
            mCurrentUser = FirebaseAuth.getInstance().currentUser!!
            mUserDatabase = FirebaseDatabase.getInstance().reference.child("users").child(mUserId)
        }
        setUpProfile()
    }

    fun findViews(){
        mProfile = findViewById(R.id.profile_picture)
        mName = findViewById(R.id.profile_name)
        mStatus = findViewById(R.id.profile_status)
    }

    fun setUpProfile(){
        mUserDatabase.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var displayName = snapshot.child("displayName").value.toString()
                var status = snapshot.child("status").value.toString()
                var profile = snapshot.child("image").value.toString()

                mName.text = displayName
                mStatus.text = status
                Glide.with(this@ProfileActivity)
                        .load(profile)
                        .placeholder(R.drawable.profile_img)
                        .into(mProfile)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}