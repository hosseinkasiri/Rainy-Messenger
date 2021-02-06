package com.example.rainymessanger.controller

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.rainymessanger.R
import com.example.rainymessanger.controller.helper.Toaster
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class LoginFragment : Fragment() {

    lateinit var mEmail: EditText
    lateinit var mPassword: EditText
    lateinit var mLoginButton: Button

    lateinit var mAuth: FirebaseAuth

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
        var view = inflater.inflate(R.layout.fragment_login, container, false)
        findViews(view)
        mAuth = FirebaseAuth.getInstance()

        mLoginButton.setOnClickListener(View.OnClickListener {
            var email = mEmail.text.toString()
            var password = mPassword.text.toString()
            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
                loginUser(email, password)
            else
                Toaster.makeToast(activity!!, "please inter your information ")
        })
        return view
    }

    private fun findViews(view: View) {
        mEmail = view.findViewById(R.id.login_email_input)
        mPassword = view.findViewById(R.id.login_password_input)
        mLoginButton = view.findViewById(R.id.login_button)
    }

    private fun loginUser(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        var userName = email.split("@")[0]
                        var intent = DashboardActivity.newIntent(activity!!, userName)
                        startActivity(intent)
                        activity!!.finish()
                    }else
                        Toaster.makeToast(activity!!, "email or password is incorrect")
                }
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}