package com.androidtutorials.androids.notesappusingmvvm.constant

import android.content.Context
import android.widget.Toast

object Constants {
    const val SPLASH_TIME_OUT:Long = 3000
    fun toast(context:Context,message:String){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
}