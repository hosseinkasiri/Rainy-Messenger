package com.example.rainymessanger.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rainymessanger.R
import com.example.rainymessanger.model.FriendlyMessage
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import de.hdodenhof.circleimageview.CircleImageView

class ChatActivity : AppCompatActivity() {

    lateinit var mCurrentUser: FirebaseUser
    lateinit var mDatabase: DatabaseReference
    lateinit var mUserDatabase: DatabaseReference
    lateinit var mReceiverUserId: String
    lateinit var mReceiverUserName: String
    lateinit var mLinearLayoutManager: LinearLayoutManager
    lateinit var mFirebaseAdapter: FirebaseRecyclerAdapter<FriendlyMessage, MessageHolder>
    lateinit var mCurrentUserName: String

    lateinit var mMessageRecycler: RecyclerView
    lateinit var mSendButton: Button
    lateinit var mEditText: EditText

    companion object{
        val EXT_ID = "userId"
        val EXT_NAME = "name"
        val EXT_STATUS = "status"
        val EXT_PROFILE = "profile"

        fun newIntent(context: Context, userId: String, name: String, status: String, profile: String) : Intent {
            var intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(EXT_ID, userId)
            intent.putExtra(EXT_NAME, name)
            intent.putExtra(EXT_STATUS, status)
            intent.putExtra(EXT_PROFILE, profile)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        findViews()
        supportActionBar!!.title = "Message"
        mCurrentUser = FirebaseAuth.getInstance().currentUser!!
        mReceiverUserId = intent.extras!!.getString(EXT_ID).toString()
        mReceiverUserName = intent.extras!!.getString(EXT_NAME).toString()
        mLinearLayoutManager = LinearLayoutManager(this)
        mLinearLayoutManager.stackFromEnd = true
        mDatabase = FirebaseDatabase.getInstance().reference
        mUserDatabase = FirebaseDatabase.getInstance().reference.child("users").child(mCurrentUser.uid)

        mUserDatabase.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mCurrentUserName = snapshot.child("displayName").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        mFirebaseAdapter = object: FirebaseRecyclerAdapter<FriendlyMessage, MessageHolder>(
                FriendlyMessage::class.java,
                R.layout.item_chat,
                MessageHolder::class.java,
                mDatabase.child("messages")
        ){
            override fun populateViewHolder(messageHolder: MessageHolder?, friendlyMessage: FriendlyMessage?, position: Int) {
                if (friendlyMessage!!.text != null &&
                        (friendlyMessage.sender == mCurrentUserName && friendlyMessage.receiver == mReceiverUserName) ||
                        (friendlyMessage.sender == mReceiverUserName && friendlyMessage.receiver == mCurrentUserName)){
                    messageHolder!!.bindView(friendlyMessage)
                    messageHolder.itemView.visibility = View.VISIBLE
                    var myUserId = mCurrentUser.uid
                    var isMe = friendlyMessage.id.equals(myUserId)

                    if (isMe){
                        messageHolder.mProfileRight.visibility = View.VISIBLE
                        messageHolder.mProfileLeft.visibility = View.GONE
                        messageHolder.mChatText.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT
                        messageHolder.mChatText.gravity = Gravity.CENTER_VERTICAL or Gravity.RIGHT

                        mDatabase.child("users").child(myUserId)
                                .addValueEventListener(object: ValueEventListener{
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        var imageUrl = snapshot.child("thumbImage").value.toString()
                                        var name = snapshot.child("displayName")
                                        Glide.with(applicationContext)
                                                .load(imageUrl)
                                                .placeholder(R.drawable.profile_img)
                                                .into(messageHolder.mProfileRight)
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                    }
                                })
                    }else{
                        messageHolder.mProfileRight.visibility = View.GONE
                        messageHolder.mProfileLeft.visibility = View.VISIBLE
                        messageHolder.mChatText.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
                        messageHolder.mChatText.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT

                        mDatabase.child("users").child(mReceiverUserId)
                                .addValueEventListener(object: ValueEventListener{
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        var imageUrl = snapshot.child("thumbImage").value.toString()
                                        var name = snapshot.child("displayName").value.toString()
                                        Glide.with(applicationContext)
                                                .load(imageUrl)
                                                .placeholder(R.drawable.profile_img)
                                                .into(messageHolder.mProfileLeft)
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                    }
                                })
                    }

                }

            }
        }

        mMessageRecycler.layoutManager = mLinearLayoutManager
        mMessageRecycler.adapter = mFirebaseAdapter

        mSendButton.setOnClickListener(View.OnClickListener {
            if (!intent.extras!!.get(EXT_NAME).toString().equals("")){
//                var currentUserName = intent.extras!!.get(EXT_NAME)
                var currentUserId = mCurrentUser.uid
                var friendlyMessage = FriendlyMessage(
                        currentUserId,
                        mCurrentUserName,
                        mReceiverUserName,
                        mEditText.text.toString().trim())

                mDatabase.child("messages").push().setValue(friendlyMessage)
                mEditText.setText("")
            }
        })
    }

    class MessageHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        lateinit var mChatText: TextView
        lateinit var mChatName: TextView
        lateinit var mProfileLeft: CircleImageView
        lateinit var mProfileRight: CircleImageView

        fun bindView(friendlyMessage: FriendlyMessage){
            mChatText = itemView.findViewById(R.id.chat_text)
            mChatName = itemView.findViewById(R.id.chat_name)
            mProfileLeft = itemView.findViewById(R.id.chat_profile)
            mProfileRight = itemView.findViewById(R.id.chat_profile_right)

            mChatText.text = friendlyMessage.text
            mChatName.text = friendlyMessage.sender
        }
    }

    fun findViews(){
        mMessageRecycler = findViewById(R.id.message_recycler)
        mSendButton = findViewById(R.id.message_send)
        mEditText = findViewById(R.id.message_edit_text)
    }
}