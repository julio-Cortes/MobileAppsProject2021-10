package com.example.project_01.ViewModels

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.project_01.MainActivity
import com.example.project_01.Models.Deck
import com.example.project_01.Models.Lobby
import com.example.project_01.Navigator.Navigator
import com.example.project_01.Repositories.RoomRepository


class LobbyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : RoomRepository
    lateinit var navigator: Navigator

    var MyRooms: LiveData<MutableList<Lobby>>

    init {
        repository = RoomRepository(application)
        MyRooms = repository.getRooms()
    }
    fun createLobby(name: String, password: String, deck: Deck) {

        repository.createRoom(name,password, deck)

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
