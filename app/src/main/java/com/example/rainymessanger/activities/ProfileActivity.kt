package com.example.rainymessanger.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
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

    lateinit var mUserDisplayName: String
    lateinit var mUserStatus: String
    lateinit var mUserProfile: String

    lateinit var mProfile: ImageView
    lateinit var mName: TextView
    lateinit var mStatus: TextView
    lateinit var mSendMessage: Button

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
        mSendMessage.setOnClickListener(View.OnClickListener {
            startActivity(ChatActivity.newIntent(this, mUserId, mUserDisplayName, mUserStatus, mUserProfile))
            finish()
        })
    }

    fun findViews(){
        mProfile = findViewById(R.id.profile_picture)
        mName = findViewById(R.id.profile_name)
        mStatus = findViewById(R.id.profile_status)
        mSendMessage = findViewById(R.id.profile_send_button)
    }

    fun setUpProfile(){
        mUserDatabase.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mUserDisplayName = snapshot.child("displayName").value.toString()
                mUserStatus = snapshot.child("status").value.toString()
                mUserProfile = snapshot.child("image").value.toString()

                mName.text = mUserDisplayName
                mStatus.text = mUserStatus
                Glide.with(this@ProfileActivity)
                        .load(mUserProfile)
                        .placeholder(R.drawable.profile_img)
                        .into(mProfile)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}