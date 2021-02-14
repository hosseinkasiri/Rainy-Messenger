package com.example.rainymessanger.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rainymessanger.R
import com.example.rainymessanger.adapters.ChatAdapter
import com.example.rainymessanger.adapters.UserAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ChatsFragment : Fragment() {

    lateinit var mDatabase: DatabaseReference
    lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mDatabase = FirebaseDatabase.getInstance().reference.child("users")
        mRecyclerView = view.findViewById(R.id.chat_recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.adapter = ChatAdapter(mDatabase, context!!)
    }
}