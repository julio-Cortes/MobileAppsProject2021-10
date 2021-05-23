package com.example.project_01.Repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.project_01.Database.Database
import com.example.project_01.Database.LobbyDao
import com.example.project_01.Models.Lobby
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RoomRepository(application: Application) {

    private var rooms :LiveData<MutableList<Lobby>>
    private val database: Database
    private val lobbyDao: LobbyDao
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        database = Room.databaseBuilder(application, Database::class.java, "planning_poker-db").build()
        lobbyDao = database.RoomDao()
        rooms = lobbyDao.getAll()
    }


    fun createRoom(name:String, password:String){
        executor.execute{
            lobbyDao.insert(Lobby(0,name, password))
        }
    }
    fun getRooms():LiveData<MutableList<Lobby>> {
        return rooms
    }

    fun deleteRoom(id:Long){
        executor.execute{
            lobbyDao.delete(id)
        }
    }
}