package com.example.project_01.ViewModels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.project_01.Deserializers.CreateRoomCredentials
import com.example.project_01.Deserializers.UserCredentials
import com.example.project_01.MainActivity
import com.example.project_01.Models.Deck
import com.example.project_01.Models.Lobby
import com.example.project_01.Navigator.Navigator
import com.example.project_01.Repositories.RoomRepository
import com.google.gson.Gson


class LobbyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : RoomRepository
    lateinit var navigator: Navigator
    lateinit var room_id : String

    var MyRooms: LiveData<MutableList<Lobby>>

    init {
        repository = RoomRepository(application)
        MyRooms = repository.getRooms()
    }
    fun createLobby(token : String, name: String, password: String, deck: Deck) {

        val response = repository.createRoom(token, name,password, deck)
        val gson = Gson()
        val result = gson.fromJson(response, CreateRoomCredentials::class.java)
        room_id = result.room_id

    }

    fun deleteLobby(id:Long){

        repository.deleteRoom(id)

    }

    fun setNavigator(activity: MainActivity){
        navigator = Navigator(activity)
    }

    fun LobbyFragmentToCreateLobbyFragment(view: View) {
        navigator.goToCreateLobbyFragment(view)
    }


}
