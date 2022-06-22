package com.example.loginvalidation.repository

import androidx.lifecycle.LiveData
import com.example.loginvalidation.RetrofitService
import com.example.loginvalidation.RetrofitService.Companion.retrofitService
import com.example.loginvalidation.roomdb.PersonDao
import com.example.loginvalidation.roomdb.User

class MainRepository constructor(private val dao : PersonDao) {

    fun getAllMovies() = retrofitService?.getAllMovies()

    fun getUser(email : String?) : LiveData<User>{
        return dao.getPerson(email)
    }
}