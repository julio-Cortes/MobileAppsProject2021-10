package com.example.project_01.Repositories

import android.app.Application
import androidx.fragment.app.activityViewModels
import com.example.project_01.ViewModels.LobbyViewModel
import com.example.project_01.ViewModels.UsersViewModel

class GeneralRepository(application: Application) {
    lateinit var room_id : String
    lateinit var userToken:String
    lateinit var userId:String
    var viewModel = LobbyViewModel(application)
    var userViewModel =  UsersViewModel(application)

    init{
        room_id = viewModel.room_id
        userToken = userViewModel.userToken
        userId = userViewModel.userId
    }
}