package com.example.loginvalidation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.loginvalidation.model.Movie
import com.example.loginvalidation.model.MovieList
import com.example.loginvalidation.repository.MainRepository
import com.example.loginvalidation.roomdb.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivityViewModel(private val repository: MainRepository,private val eMail : String?) : ViewModel() {

    val movieList = MutableLiveData<List<Movie>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllMovies(){
        val response = repository.getAllMovies()

        response?.enqueue(object : Callback<MovieList>{
            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                movieList.postValue(response.body()?.mList)  //difference bw set value and post value.
            }

            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getUserDetails() : LiveData<User>{
        return repository.getUser(eMail)
    }
}