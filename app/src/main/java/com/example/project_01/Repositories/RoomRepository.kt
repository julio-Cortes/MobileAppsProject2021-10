package com.example.project_01.Repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.project_01.Database.Database
import com.example.project_01.Database.LobbyDao
import com.example.project_01.Deserializers.DecksCredentials
import com.example.project_01.Deserializers.LobbyCredentials
import com.example.project_01.Deserializers.LobbyListCredentials
import com.example.project_01.Models.Deck
import com.example.project_01.Models.Lobby
import com.example.project_01.Reftrofit.LobbyRemoteRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class RoomRepository(application: Application, room:Database, lobbyDao:LobbyDao, lobbyRemoteRepository: LobbyRemoteRepository) {
    val app = application
    private var rooms = lobbyDao.getAll()
    private val database = room
    var roomId : String? = null
    private val lobbyDao = lobbyDao
    private val service = lobbyRemoteRepository
    lateinit var response: Response<ResponseBody>
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    val cm = app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager




    suspend fun createRoom(token: String, name:String, password:String, name_deck: String, cards_deck : List<String>) {
        val networkInfo = cm!!.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            val jsonObject = JSONObject()
            val jsonObject_aux = JSONObject()

            jsonObject.put("roomName", name)
            jsonObject.put("password", password)
            jsonObject_aux.put("name", name_deck)
            jsonObject_aux.put("cards", cards_deck)
            val aux = DecksCredentials(name_deck,cards_deck as MutableList<String>)
            jsonObject.put("deck", aux)
            Toast.makeText(app.applicationContext,jsonObject.toString(), Toast.LENGTH_SHORT).show()
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            response = service.createRoom(token, body)
            if (response.code() == 200){
                val respuesta = response.body()?.string()
                if(respuesta!=""){
                    val gson = Gson()
                    val lobby = gson.fromJson(respuesta, LobbyCredentials::class.java)
                    val deck_deck = Deck(0,name_deck, cards_deck.toString())
                    executor.execute{
                        lobbyDao.insert(Lobby(0,lobby.roomId,name, password, deck_deck))
                    }
                    roomId = lobby.roomId

                }
            }
            roomId = null
        }
        else {
            executor.execute{
                val deck_deck = Deck(0,name_deck, cards_deck.toString())
                lobbyDao.insert(Lobby(0,null,name, password, deck_deck))
            }
            roomId = null

        }


    }

    suspend fun joinRoom(token: String, name:String, password:String)  {
        val networkInfo = cm!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            val jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("password", password)
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val response = service.joinRoom(token, body)
            if (response.code() == 200){
                Toast.makeText(app.applicationContext,"Room joined", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(app.applicationContext,"You don´t have internet to join a room", Toast.LENGTH_SHORT).show()
        }

    }

    suspend fun getLobbyCredentials(token: String) {

        val response = service.getRooms(token)
        val body = response.body()?.string()
        val gson = Gson()
        val deck = gson.fromJson(body, LobbyListCredentials::class.java)
        if (deck != null) {
            deck.rooms?.forEach {
                executor.execute{
                    val gson = Gson()
                    //val deck = gson.fromJson(it.deck, Deck::class.java)
                    //lobbyDao.insert(Lobby(0,it.roomId,it.roomName, it.password, Deck(0,deck.name, deck.cards.toString())))
                }
            }
        }


    }



    fun getRooms():LiveData<MutableList<Lobby>> {
        return rooms
    }

    fun deleteRoom(token: String, id:Long, name: String?){
        val cm = app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            val jsonObject = JSONObject()
            jsonObject.put("roomId", id)
            jsonObject.put("name", name)
            val body = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            response = service.deleteRoom(token, body)
            executor.execute {
                lobbyDao.delete(id)
            }
        }
        else{
            executor.execute {
                lobbyDao.delete(id)
            }
        }
    }
}