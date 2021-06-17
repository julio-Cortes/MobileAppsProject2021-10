package com.example.project_01.ViewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.project_01.Deserializers.*
import com.example.project_01.Models.Lobby
import com.example.project_01.Navigator.Navigator
import com.example.project_01.Repositories.LobbyRepository
import com.example.project_01.Views.Adapters.LobbyAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@RequiresApi(Build.VERSION_CODES.N)
class LobbyViewModel(application: Application, lobbyRepository: LobbyRepository, navigator: Navigator) : AndroidViewModel(application) {
    val app = application
    private val repository = lobbyRepository
    val navigator = navigator

    var token : String
    var MyRooms: LiveData<MutableList<Lobby>>
    var members= MutableLiveData<MutableList<Members>>().apply { postValue(mutableListOf<Members>()) }
    val sharedPreferences = app?.getSharedPreferences("user_Token", Context.MODE_PRIVATE)
    val cm = app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager



    init {

        token = sharedPreferences.getString("user_Token", "").toString()
        MyRooms = repository.getRooms()
        cm?.let {
            it.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    viewModelScope.launch(Dispatchers.Main) {
                        withContext(Dispatchers.IO){
                            repository.getCreateOffline(token)
                        }

                    }
                }
            })
        }






        val networkInfo = cm!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            viewModelScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO){
                    repository.getLobbyCredentials(token)
                }

            }
        }
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                MyRooms = repository.getRooms()
            }
        }

    }


    fun createLobby(name: String, password: String, name_deck : String, cards_deck : List<String>) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                repository.createRoom(token, name,password, name_deck, cards_deck)
                MyRooms = repository.getRooms()
            }

        }
    }
    fun joinLobby(name: String, password: String, adapter: LobbyAdapter) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO  ){
                repository.joinRoom(token, name,password)
                repository.getLobbyCredentials(token)
                MyRooms = repository.getRooms()
            }

        }

    }
    fun deleteLobby(id:Long, name: String?){
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO){
                repository.deleteRoom(token, id, name)
                MyRooms = repository.getRooms()
            }

        }
    }
    fun LobbyFragmentToCreateLobbyFragment(view: View) {
        navigator.goToCreateLobbyFragment(view)
    }
    fun LobbyFragmentToJoinLobbyFragment(view: View) {
        navigator.goToJoinLobbyFragment(view)
    }
    fun LobbyFragmentToVoteFragment(view:View, lobby: Lobby){
        val networkInfo = cm!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            viewModelScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    val room = repository.getRoom(lobby.name, token)
                    repository.room = room

                }
                navigator.goToVote( view)

            }

        }
    }

    fun vote(num: String, view: View) {
        val networkInfo = cm!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            viewModelScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    repository.vote(num, token)
                    members.postValue(repository.getResult(token))

                }

            }
            navigator.goToVotingRoom(view)
        }
    }

    fun getResults() {
        val TIEMPO:Long = 5000
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                val networkInfo = cm!!.activeNetworkInfo
                if (networkInfo != null && networkInfo.isConnected) {
                    if (repository.room != null) {
                        viewModelScope.launch(Dispatchers.Main) {
                            withContext(Dispatchers.IO) {
                                repository.room =
                                    repository.getRoom(repository.room!!.roomName, token)
                                val voting_members = repository.getResult(token)
                                val aux = mutableListOf<Members>()

                                repository.room!!.members?.forEach {
                                    var flag = true
                                    for (j in voting_members){
                                        if (it == j.name){
                                            aux.add(j)
                                            flag = false
                                            break
                                        }
                                    }
                                    if (flag){
                                        aux.add(Members(it,null,null))
                                    }
                                }
                                members.postValue(aux)
                            }

                            //Toast.makeText(app.applicationContext,"POST", Toast.LENGTH_SHORT).show()
                        }
                    }

                    handler.postDelayed(this, TIEMPO)
                }
            }
        }, TIEMPO)

    }

    fun nullRoom() {
        repository.room = null
    }

    fun getRoom(): MutableList<String>? {
        return repository.room?.deck?.cards
    }
    fun getMembers(): List<String>? {
        return repository.room?.members
    }
}
