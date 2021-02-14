package com.example.rainymessanger.adapters

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rainymessanger.R
import com.example.rainymessanger.activities.ChatActivity
import com.example.rainymessanger.model.User
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView

class ChatAdapter(databaseQuery: DatabaseReference,val context: Context):
        FirebaseRecyclerAdapter<User, ChatAdapter.ChatHolder>(
        User::class.java,
        R.layout.item_chat,
        ChatHolder::class.java,
        databaseQuery
) {

    override fun populateViewHolder(chatholder: ChatHolder?, user: User?, position: Int) {
        chatholder!!.bindView(user!!, context)
        var userId = getRef(position).key
        chatholder.itemView.setOnClickListener(View.OnClickListener {
            context.startActivity(ChatActivity.newIntent(context,
                    userId!!,
                    user.displayName!!,
                    user.status!!,
                    user.thumbImage!!))
        })
    }

    class ChatHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        lateinit var mChatProfile: CircleImageView
        lateinit var mChatName:TextView

        fun bindView(user: User, context: Context){
            mChatProfile = itemView.findViewById(R.id.item_chat_profile)
            mChatName = itemView.findViewById<TextView>(R.id.item_chat_name)
            mChatName.text = user.displayName!!
            Glide.with(context)
                    .load(user.thumbImage)
                    .placeholder(R.drawable.profile_img)
                    .into(mChatProfile)
        }
    }
}