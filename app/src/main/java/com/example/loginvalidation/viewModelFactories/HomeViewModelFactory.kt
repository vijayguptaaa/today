package com.example.loginvalidation.viewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loginvalidation.repository.MainRepository
import com.example.loginvalidation.roomdb.PersonRepository
import com.example.loginvalidation.viewmodel.HomeActivityViewModel

class HomeViewModelFactory constructor(private val repository: MainRepository, private val eMail : String?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeActivityViewModel::class.java)) {
            HomeActivityViewModel(this.repository,eMail) as T
        }
        else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}