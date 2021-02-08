package com.example.rainymessanger.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.rainymessanger.R
import com.example.rainymessanger.activities.DashboardActivity
import com.example.rainymessanger.helper.Toaster
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateAccountFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth
    lateinit var mDatabase: DatabaseReference
    lateinit var mCreateButton: Button
    lateinit var mEmail: EditText
    lateinit var mPassword: EditText
    lateinit var mDisplayName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.activity_create_account, container, false)
        findViews(view)
        mAuth = FirebaseAuth.getInstance()
        mCreateButton.setOnClickListener(View.OnClickListener {
            var email = mEmail.text.toString().trim()
            var password = mPassword.text.toString().trim()
            var name = mDisplayName.text.toString().trim()
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(name))
                createAccount(email, password, name)
            else
                Toast.makeText(activity, "please enter your information", Toast.LENGTH_SHORT).show()
        })
        return view
    }

    private fun findViews(view: View) {
        mCreateButton = view.findViewById(R.id.create_account_btn)
        mEmail = view.findViewById(R.id.account_display_email)
        mPassword = view.findViewById(R.id.account_display_password)
        mDisplayName = view.findViewById(R.id.account_display_name)
    }

    private fun createAccount(email: String, password: String, name: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    var currentUser = mAuth.currentUser
                    var userId = currentUser!!.uid
                    mDatabase = FirebaseDatabase.getInstance().reference.child("users").child(userId)
                    var userObject = HashMap<String, String>()
                    userObject.put("displayName", name)
                    userObject.put("image", "default")
                    userObject.put("status", "i`m programmer ...")
                    userObject.put("thumbImage", "default")
                    mDatabase.setValue(userObject).addOnCompleteListener {
                        if (it.isSuccessful) {
                            var intent = DashboardActivity.newIntent(activity!!, name)
                            startActivity(intent)
                            activity!!.finish()
                        }
                        else
                            Toaster.makeToast(activity!!, "user not created")
                    }
                }else
                    Log.d("error", it.exception.toString())
            }
    }

    companion object {
        fun newInstance(): CreateAccountFragment {
            return CreateAccountFragment()
        }
    }
}