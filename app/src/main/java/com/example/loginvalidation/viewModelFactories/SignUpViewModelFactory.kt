package com.example.loginvalidation.viewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loginvalidation.roomdb.PersonRepository
import com.example.loginvalidation.viewmodel.SignUpViewModel

class SignUpViewModelFactory(val repository: PersonRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpViewModel(repository) as T
    }
}