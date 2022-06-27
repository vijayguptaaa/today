package com.example.loginvalidation.view

import android.app.Activity
import android.app.WallpaperColors.fromBitmap
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.loginvalidation.R
import com.example.loginvalidation.databinding.ActivitySignupBinding
import com.example.loginvalidation.roomdb.PersonDatabase
import com.example.loginvalidation.roomdb.PersonRepository
import com.example.loginvalidation.roomdb.User
import com.example.loginvalidation.viewModelFactories.SignUpViewModelFactory
import com.example.loginvalidation.viewmodel.SignUpViewModel
import com.example.loginvalidation.utils.Converters.imageToString

class SignupActivity : AppCompatActivity() {


    private lateinit var mViewBinding: ActivitySignupBinding
    private lateinit var mViewModel: SignUpViewModel
    private lateinit var image: ImageView


    companion object{
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST = 2
        private const val IMAGE_REQUEST_CODE = 3
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        image = mViewBinding.image
//        val imageToString = imageToString(mViewBinding.image)
        val dao = PersonDatabase.getInstance(application).personDao()
        val repository = PersonRepository(dao)
        mViewModel = ViewModelProvider(this,SignUpViewModelFactory(repository))[SignUpViewModel::class.java]

        mViewBinding.signupViewModel = mViewModel
        mViewBinding.lifecycleOwner = this

//        mViewModel.setImage(imageToString)



        mViewModel.onSignUpResponse.observe(this, Observer {
            if (it) {
//                val imageToString = imageToString(mViewBinding.image)
//                val user = User(0,imageToString,mViewBinding.firstName.text.toString(),mViewBinding.lastName.text.toString()
//                ,mViewBinding.emailId.text.toString(),mViewBinding.password.text.toString()
//                    )
//                mViewModel.insertUser(user)
                Toast.makeText(this,"Registration Successfull",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        })
        mViewModel.onLoginResponse.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        })


        mViewModel.onClickImageResponse.observe(this, Observer {

        })
        image.setOnClickListener{

//            pickImageGallery()
            /*Checking the permission is Granted or Not*/
            if(ContextCompat.checkSelfPermission(
                    this,android.Manifest.permission.CAMERA
                )== PackageManager.PERMISSION_GRANTED){
                /*use this intent for starting the camera*/
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST)
            }else
            {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== CAMERA_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST)
            }else{
                Toast.makeText(this,"You just denied the permission for camera" + "Don't Worry you can allow it in the settings",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA_REQUEST ){
                val thumbnail : Bitmap = data!!.extras!!.get("data")as Bitmap
                image.setImageBitmap(thumbnail)
            }
        }
    }


//    private fun pickImageGallery(){
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, IMAGE_REQUEST_CODE)
//    }
}