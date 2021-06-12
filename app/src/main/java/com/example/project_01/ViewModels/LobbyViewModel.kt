package com.example.project_01.ViewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.project_01.Deserializers.*
import com.example.project_01.MainActivity
import com.example.project_01.Models.Deck
import com.example.project_01.Models.Lobby
import com.example.project_01.Navigator.Navigator
import com.example.project_01.Repositories.GeneralRepository
import com.example.project_01.Repositories.RoomRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch


class LobbyViewModel(application: Application, roomRepository: RoomRepository, navigator: Navigator) : AndroidViewModel(application) {
    val app = application
    private val repository = roomRepository
    private val generalRepository : GeneralRepository
    val navigator = navigator
    lateinit var room_id : String
    var user_Token : String
    var MyRooms: LiveData<MutableList<LobbyCredentials>>

    init {
        MyRooms = repository.getRooms()
        generalRepository = GeneralRepository(application)
        user_Token = generalRepository.userToken
        val cm = app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            viewModelScope.launch {
                repository.getLobbyCredentials(user_Token)
            }
        }
        else{
            viewModelScope.launch {
                MyRooms = repository.getRooms()
            }
        }
    }
    fun createLobby(token : String, name: String, password: String, deck: DecksCredentials) {
        val response = repository.createRoom(token, name,password, deck)
        if(response!=""){
            val gson = Gson()
            val result = gson.fromJson(response, CreateRoomCredentials::class.java)
            room_id = result.room_id
        }
    }
    fun joinLobby(token : String, name: String, password: String) {
        val response = repository.joinRoom(token, name,password)
        if(response!=""){
            val gson = Gson()
            val result = gson.fromJson(response, JoinLobbyCredentials::class.java)
            val members = result.members
        }
    }
    fun deleteLobby(id:String){
        repository.deleteRoom(id)
    }
    fun LobbyFragmentToCreateLobbyFragment(view: View) {
        navigator.goToCreateLobbyFragment(view)
    }
    fun LobbyFragmentToJoinLobbyFragment(view: View) {
        navigator.goToJoinLobbyFragment(view)
    }
}
