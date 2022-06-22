package com.example.loginvalidation.view

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.loginvalidation.R
import com.example.loginvalidation.databinding.ActivitySignupBinding
import com.example.loginvalidation.roomdb.PersonDatabase
import com.example.loginvalidation.roomdb.PersonRepository
import com.example.loginvalidation.viewModelFactories.SignUpViewModelFactory
import com.example.loginvalidation.viewmodel.SignUpViewModel
import java.util.jar.Manifest

class SignupActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivitySignupBinding
    private lateinit var mViewModel: SignUpViewModel
    private val cameraRequest = 1888
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        val dao = PersonDatabase.getInstance(application).personDao()
        val repository = PersonRepository(dao)
        mViewModel = ViewModelProvider(this,SignUpViewModelFactory(repository))[SignUpViewModel::class.java]

        mViewBinding.signupViewModel = mViewModel
        mViewBinding.lifecycleOwner = this



        mViewModel.onSignUpResponse.observe(this, Observer {
            if (it) {
                Toast.makeText(this,"Registration Successfull",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        })
        mViewModel.onLoginResponse.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        })

    }
}