package com.example.rainymessanger.controller.helper

import android.content.Context
import android.widget.Toast

abstract class Toaster {
    companion object{
        fun makeToast(context: Context, string: String){
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
        }
    }
}