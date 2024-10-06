package com.androidtutorials.androids.notesappusingmvvm.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.androidtutorials.androids.notesappusingmvvm.R
import com.androidtutorials.androids.notesappusingmvvm.constant.Constants.SPLASH_TIME_OUT
import com.androidtutorials.androids.notesappusingmvvm.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private lateinit var mBinder: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        initUI()
        return mBinder.root
    }

    private fun initUI() {
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }, SPLASH_TIME_OUT)
    }
}