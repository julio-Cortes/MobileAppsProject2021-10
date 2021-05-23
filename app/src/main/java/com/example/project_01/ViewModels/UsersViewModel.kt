package com.example.project_01.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.project_01.Models.User
import com.example.project_01.Repositories.UserRepository

class UsersViewModel(application: Application):  AndroidViewModel(application) {

    private val repository : UserRepository
    lateinit var currentUser: User

    init {
        repository = UserRepository(application)
    }

}