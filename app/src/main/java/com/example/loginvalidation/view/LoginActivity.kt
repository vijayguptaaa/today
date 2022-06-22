package com.example.loginvalidation.view
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.loginvalidation.LanguageActivity
import com.example.loginvalidation.R
import com.example.loginvalidation.databinding.ActivityLoginBinding
import com.example.loginvalidation.roomdb.PersonDatabase
import com.example.loginvalidation.roomdb.PersonRepository
import com.example.loginvalidation.roomdb.User
import com.example.loginvalidation.viewModelFactories.LoginViewModelFactory
import com.example.loginvalidation.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var mViewBinding: ActivityLoginBinding
    private lateinit var mViewModel: LoginViewModel


    override fun onStart() {
        super.onStart()
        val sharedPreferences : SharedPreferences = getSharedPreferences("loginData", Context.MODE_PRIVATE)
        val isLogin = sharedPreferences.getBoolean("isLogin",false)
        val userEmail = sharedPreferences.getString("userEmail","")
        if(isLogin){
            val intent = Intent(this,HomeActivity::class.java).apply {
                putExtra("userEmail",userEmail)
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val dao = PersonDatabase.getInstance(application).personDao()
        val repository = PersonRepository(dao)
        mViewModel =
            ViewModelProvider(this, LoginViewModelFactory(repository))[LoginViewModel::class.java]

        mViewBinding.loginViewModel = mViewModel
        mViewBinding.lifecycleOwner = this

        mViewModel.onSignUpResponse2.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, SignupActivity::class.java))
            }
        })

        mViewModel.liveUser.observe(this, Observer {
            if (it != null) {
                saveLogin(it)
                val intent = Intent(this, HomeActivity::class.java).apply {
                    putExtra("userEmail", it.email)
                }
                startActivity(intent)
                finish()
            }
            else Toast.makeText(this, "Please register this Email Address", Toast.LENGTH_SHORT)
                .show()
        })


    }

    /*saving data in shared preference after login*/
    private fun saveLogin(user: User) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("loginData", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor? = sharedPreferences.edit()
        editor?.putBoolean("isLogin", true)
        editor?.putString("userEmail", user.email)
        editor?.apply()
    }
}
