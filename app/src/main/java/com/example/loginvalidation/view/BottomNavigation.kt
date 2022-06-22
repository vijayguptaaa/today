package com.example.loginvalidation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.loginvalidation.R
import com.example.loginvalidation.databinding.ActivityBottomNavigationBinding
import com.example.loginvalidation.view.fragments.BreakingNewsFragment
import com.example.loginvalidation.view.fragments.HomeFragment

class BottomNavigation : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_bottom_navigation)
        setContentView(R.layout.activity_bottom_navigation)
        binding.bottomNavigationView.setupWithNavController(binding.flFragment.findNavController())
    }
}