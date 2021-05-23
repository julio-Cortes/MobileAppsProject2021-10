package com.example.project_01.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.project_01.Models.Lobby
import com.example.project_01.Repositories.RoomRepository


class RoomsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : RoomRepository

    var MyRooms: LiveData<MutableList<Lobby>>

    init {
        repository = RoomRepository(application)
        MyRooms = repository.getRooms()
    }
    fun createLobby(name:String, password:String) {

        repository.createRoom(name,password)

    }

    fun deleteLobby(id:Long){

        repository.deleteRoom(id)

    }


}
