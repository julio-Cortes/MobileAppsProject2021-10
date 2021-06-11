package com.example.project_01.Repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.miniproject03.Retrofit.ServiceBuilder
import com.example.project_01.Database.Database
import com.example.project_01.Database.JoinedRoomDao
import com.example.project_01.Database.LobbyDao
import com.example.project_01.Deserializers.DecksCredentials
import com.example.project_01.Deserializers.LobbyCredentials
import com.example.project_01.Models.Deck
import com.example.project_01.Models.Lobby
import com.example.project_01.Reftrofit.LobbyRemoteRepository
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class RoomRepository(application: Application, room:Database, lobbyDao:LobbyDao, getlobbyDao:JoinedRoomDao) {
    val app = application
    private var rooms_aux :LiveData<MutableList<Lobby>>
    //private var rooms : LiveData<MutableList<LobbyCredentials>>
    private val database: Database
    private val lobbyDao = lobbyDao
    private val getLobbyDao =getlobbyDao
    private val service: LobbyRemoteRepository
    lateinit var response: Response<ResponseBody>

    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        database = room
        rooms_aux = lobbyDao.getAll()
        //getLobbyCredentials()
        //rooms = getLobbyDao.getAll()
        service = ServiceBuilder.getRetrofit().create(LobbyRemoteRepository::class.java)

    }


    fun createRoom(token: String, name:String, password:String, deck: DecksCredentials) : String? {
        val cm = app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm!!.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            // Si hay conexión a Internet en este momento
            val jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("password", password)
            jsonObject.put("deck", deck)
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            response = service.createRoom(token, body)
            if (response.code() == 200){
                return response.body()?.string()
            }
            else{
                return ""
            }
            executor.execute{
                val deck_deck = Deck(deck.name, deck.cards.toString())
                lobbyDao.insert(Lobby(0,name, password, deck_deck))
            }

        } else {
            // No hay conexión a Internet en este momento
            executor.execute{
                val deck_deck = Deck(deck.name, deck.cards.toString())
                lobbyDao.insert(Lobby(0,name, password, deck_deck))
            }
            return ""
        }
    }

    fun joinRoom(token: String, name:String, password:String) : String? {
        val cm = app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm!!.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            val jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("password", password)
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            response = service.joinRoom(token, body)
            if (response.code() == 200){
                return response.body()?.string()
            }
            else{
                return ""
            }

        } else {
            Toast.makeText(app.applicationContext,"You don´t have internet to joina room", Toast.LENGTH_SHORT).show()
            return ""
        }

    }
    fun getLobbyCredentials() {
        val jsonObject = JSONObject()
        val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val call = service.getRooms()
        val response = call.body()?.string()
        val gson = Gson()
        val Rooms = gson.fromJson(response, Array<LobbyCredentials>::class.java)
        executor.execute{
            Rooms.forEach {
                getLobbyDao.insert(it)
            }
        }
    }

    fun getRooms_aux():LiveData<MutableList<Lobby>> { //CAMBIAR
        return rooms_aux
    }
    //fun getRooms():LiveData<MutableList<LobbyCredentials>> {
        //return rooms
    //}

    fun deleteRoom(id:Long){
        executor.execute{
            lobbyDao.delete(id)
        }
    }
}