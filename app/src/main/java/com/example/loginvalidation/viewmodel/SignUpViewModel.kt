package com.example.loginvalidation.viewmodel

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.text.TextUtils
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginvalidation.databinding.ActivitySignupBinding
import com.example.loginvalidation.models.UserErrorModel
import com.example.loginvalidation.roomdb.User
import com.example.loginvalidation.roomdb.PersonRepository
import com.example.loginvalidation.utils.Validation
import com.example.loginvalidation.view.SignupActivity
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: PersonRepository) : ViewModel()  {

    var firstName : MutableLiveData<String> = MutableLiveData()
    var lastName : MutableLiveData<String> = MutableLiveData()
    var email : MutableLiveData<String> = MutableLiveData()
    var phone : MutableLiveData<String> = MutableLiveData()
    var password : MutableLiveData<String> = MutableLiveData()
    var image : MutableLiveData<String> = MutableLiveData()
    var confirmPassword : MutableLiveData<String> = MutableLiveData()
    var userError : MutableLiveData<UserErrorModel> = MutableLiveData()
    private lateinit var user : User
    val onClickImageResponse : MutableLiveData<Boolean> = MutableLiveData()
    val onSignUpResponse : MutableLiveData<Boolean> = MutableLiveData()
    val onLoginResponse : MutableLiveData<Boolean> = MutableLiveData()

    init {
        onSignUpResponse.value = false
        onLoginResponse.value = false
        userError.value = UserErrorModel()
    }
    fun onClickImage(){
        onClickImageResponse.value = true
    }
    // Format validation of First Name
    private fun validate() : Boolean {
        val userErrorMessage = UserErrorModel()

        val isValidFirstName = TextUtils.isEmpty(firstName.value)
        val isValidLastName = TextUtils.isEmpty(lastName.value)
        val isValidEmail = Validation.isValidEmail(email.value.toString())
        val isValidPhone = Validation.isValidPhone(phone.value.toString())
        val isValidPassword = Validation.isValidPassword(password.value.toString())
        val isValidConfirmPassword = Validation.isConfirmPassword(password.value.toString(),confirmPassword.value.toString())

        if(isValidFirstName){
            userErrorMessage.firstNameErrorMessage = "Field cannot be empty"
        }
        if(isValidLastName){
            userErrorMessage.lastNameErrorMessage = "Field Cannot be empty"
        }
        if(!isValidPhone){
            userErrorMessage.phoneErrorMessage = "Invalid Phone Number"
        }
        if(!isValidEmail){
            userErrorMessage.emailErrorMessage = "Invalid Email"
        }
        if(!isValidPassword){
            userErrorMessage.passwordErrorMessage = "Invalid Password"
        }
        if(!isValidConfirmPassword){
            userErrorMessage.confirmPasswordErrorMessage = "Please Confirm Password"
        }
        userError.value = userErrorMessage
        return !isValidFirstName && !isValidLastName && isValidPhone && isValidEmail && isValidPassword && isValidConfirmPassword
    }
    fun signupButton(){
        if (validate()){
            insertUser()
            onSignUpResponse.value = true
        }
        onSignUpResponse.value = validate()
    }
    fun loginButton(){
        onLoginResponse.value = true
    }
    // Save Data into Database
     fun insertUser() {
        CoroutineScope(Dispatchers.IO).launch {
            user = User(0,image.value.toString(),firstName.value.toString().trim(),lastName.value.toString().trim()
                ,email.value.toString().trim(),password.value.toString().trim())
            repository.insert(user)
            repository.insert(this@SignUpViewModel.user)

        }
    }


//fun insertUser(user: User){
//    CoroutineScope(Dispatchers.IO).launch {
//        repository.insert(user)
//    }
//}
//    fun setImage(imageToString : String){
//        image.value = imageToString
//    }
}
