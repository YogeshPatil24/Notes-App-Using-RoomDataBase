package com.androidtutorials.androids.notesappusingmvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.androidtutorials.androids.notesappusingmvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinder: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.fragment_container)
        setContentView(mBinder.root)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}