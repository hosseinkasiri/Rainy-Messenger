package com.example.rainymessanger.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rainymessanger.R
import com.example.rainymessanger.activities.ChatActivity
import com.example.rainymessanger.activities.ProfileActivity
import com.example.rainymessanger.helper.Toaster
import com.example.rainymessanger.model.User
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView


class UserAdapter(databaseQuery: DatabaseReference, val context: Context): FirebaseRecyclerAdapter
<User, UserAdapter.UserHolder>(
        User::class.java,
        R.layout.item_user,
        UserHolder::class.java,
        databaseQuery
){

    override fun populateViewHolder(userHolder: UserHolder?,user: User?, position: Int) {
        var userId = getRef(position).key
        userHolder!!.bindView(user!!, context)
        userHolder.itemView.setOnClickListener {
            var options = arrayOf("Open Profile", "Send Message")
            var builder = AlertDialog.Builder(context)
            builder.setTitle("Select Options")
            builder.setItems(options, DialogInterface.OnClickListener { dialogInterface, i ->
                var userName = userHolder.mUserNameTxt
                var userStatus = userHolder.mUserStatusTxt
                var profilePic = userHolder.mProfileTxt

                if (i == 0){
                    var profileIntent = ProfileActivity.newIntent(context, userId!!)
                    context.startActivity(profileIntent)
                }else{
                    var chatIntent = ChatActivity.newIntent(context,
                            userId!!,
                            userName,
                            userStatus,
                            profilePic)
                    context.startActivity(chatIntent)
                }
            })
            builder.show()
        }
    }

    class UserHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        lateinit var mUserNameTxt: String
        lateinit var mUserStatusTxt: String
        lateinit var mProfileTxt: String

        fun bindView(user: User, context: Context){
            var userName = itemView.findViewById<TextView>(R.id.user_display_name)
            var userStatus = itemView.findViewById<TextView>(R.id.user_status)
            var userProfile = itemView.findViewById<CircleImageView>(R.id.user_profile)

            mUserNameTxt = user.displayName!!
            mUserStatusTxt = user.status!!
            mProfileTxt = user.thumbImage!!

            userName.text = user.displayName
            userStatus.text = user.status

            Glide.with(context)
                    .load(mProfileTxt)
                    .placeholder(R.drawable.profile_img)
                    .into(userProfile)
        }
    }
}