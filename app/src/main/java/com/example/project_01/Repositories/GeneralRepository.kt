package com.example.project_01.Repositories

import android.app.Application
import android.content.Context
import androidx.fragment.app.activityViewModels
import com.example.project_01.ViewModels.LobbyViewModel
import com.example.project_01.ViewModels.UsersViewModel

class GeneralRepository(application: Application) {
    val app = application
    val sharedPreferences = app?.getSharedPreferences("user_Token", Context.MODE_PRIVATE)
    //lateinit var room_id : String
    lateinit var userToken:String
    //lateinit var userId:String

    init{
        userToken = sharedPreferences.getString("user_Token", "").toString()
    }
}