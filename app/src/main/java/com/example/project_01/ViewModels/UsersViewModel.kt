package com.example.project_01.ViewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_01.Models.User
import com.example.project_01.Repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel(application: Application):  AndroidViewModel(application) {

    private val repository : UserRepository
    lateinit var currentUser: User

    init {
        repository = UserRepository(application)
    }
    fun SignUp(username:String, name:String, password:String){
        repository.SignUp(username,name,password)

    }
    fun LogIn(username: String, password: String){

        val a = repository.LogIn(username,password)

    }

}