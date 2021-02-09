package com.example.rainymessanger.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.rainymessanger.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StatusDialogFragment : DialogFragment() {

    lateinit var mEditStatus: EditText
    lateinit var mSaveStatus: Button

    lateinit var mDatabase: DatabaseReference
    lateinit var mCurrentUser: FirebaseUser

    companion object{
        val EXTRA_STATUS = "status"
        fun newInstance(status: String): StatusDialogFragment{
            val args = Bundle()
            args.putString(EXTRA_STATUS, status)
            val fragment = StatusDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var status = arguments!!.getString(EXTRA_STATUS)
        var builder = AlertDialog.Builder(activity!!)
//        var view = layoutInflater.inflate(R.layout.fragment_status_dialog, null)
        val view = LayoutInflater.from(activity).inflate(R.layout.fragment_status_dialog, null)
        findViews(view)
        mEditStatus.setText(status)
        mSaveStatus.setOnClickListener(View.OnClickListener {
            mCurrentUser = FirebaseAuth.getInstance().currentUser!!
            var userId = mCurrentUser.uid
            mDatabase = FirebaseDatabase.getInstance().reference
                    .child("users")
                    .child(userId)
            var status = mEditStatus.text.toString().trim()
            mDatabase.child("status")
                    .setValue(status)
            dismiss()
        })
        builder.setView(view)
        return builder.create()
    }

    private fun findViews(view: View) {
        mEditStatus = view.findViewById(R.id.dialog_edit_status)
        mSaveStatus = view.findViewById(R.id.dialog_save_status)
    }
}