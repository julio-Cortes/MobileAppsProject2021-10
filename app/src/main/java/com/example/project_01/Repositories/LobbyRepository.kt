package com.example.project_01.Repositories

import android.app.Application
import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.myapplication.service.LocationService
import com.example.project_01.Database.LobbyDao
import com.example.project_01.Deserializers.*
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


class LobbyRepository(application: Application, lobbyDao:LobbyDao, lobbyRemoteRepository: LobbyRemoteRepository) {
    val app = application
    private var rooms = lobbyDao.getAll()
    private val lobbyDao = lobbyDao
    private val service = lobbyRemoteRepository
    lateinit var response: Response<ResponseBody>

    val cm = app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var room: LobbyCredentials? = null
    var roomId : String? = null
    var members =  mutableListOf<Members>()




    suspend fun createRoom(token: String, name:String, password:String, name_deck: String, cards_deck : List<String>) {
        val networkInfo = cm!!.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            lobbyDao.clear()
            val jsonObject = JSONObject()
            jsonObject.put("roomName", name)
            jsonObject.put("password", password)
            val body = CreateLobbyCredentials(name, password, DecksCredentials(name_deck, cards_deck as MutableList<String>))
            val response = service.createRoom(token, body)
            if (response.code() == 200){
                val respuesta = response.body()?.string()
                if(respuesta!=""){
                    val gson = Gson()
                    val lobby = gson.fromJson(respuesta, LobbyCredentials::class.java)
                    val deck_deck = Deck( name_deck, cards_deck.toString().replace(" ",""),)
                    lobbyDao.insert(Lobby(0,lobby.roomId,name, password, deck_deck))
                    roomId = lobby.roomId


                }
            }
            roomId = null
        }
        else {
            val deck_deck = Deck(name_deck,cards_deck.toString().replace(" ","") )
            lobbyDao.insert(Lobby(0,null,name, password, deck_deck))
            roomId = null

        }


    }

    suspend fun joinRoom(token: String, name: String, password: String)  {
        val networkInfo = cm!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            val jsonObject = JSONObject()
            jsonObject.put("roomName", name)
            jsonObject.put("password", password)
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val response = service.joinRoom(token, body)
            if (response.code() == 200){
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(app.applicationContext, "Room joined", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(app.applicationContext,"You don´t have internet to join a room", Toast.LENGTH_SHORT).show()
            }

        }

    }

    suspend fun getLobbyCredentials(token: String){
        val networkInfo = cm!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            val response = service.getRooms(token)
            if (response.code() == 200){
                val body = response.body()?.string()
                val gson = Gson()
                val deck = gson.fromJson(body, LobbyListCredentials::class.java)
                if (deck != null) {
                    deck.rooms?.forEach {
                        val lob = lobbyDao.check(it.roomId)
                        if (lob==null){
                            val sub_response = service.getRoom(it.roomName, token)
                            val lobby = gson.fromJson(sub_response.body()?.string(), LobbyCredentials::class.java)
                            var insert =Lobby(0,it.roomId,it.roomName,null,Deck(lobby.deck!!.name, lobby.deck?.cards.toString()))
                            lobbyDao.insert(insert)
                        }
                    }
                }
            }
        }
    }



    fun getRooms():LiveData<MutableList<Lobby>> {
        return rooms
    }

    suspend fun deleteRoom(token: String, id:Long, name: String?){
        val networkInfo = cm!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            val jsonObject = JSONObject()
            jsonObject.put("roomId", id)
            jsonObject.put("roomName", name)
            val body = jsonObject.toString()
                .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val response = service.deleteRoom(token, body)
                lobbyDao.delete(id)
        }
        else{
            lobbyDao.deleteOffline(id)
        }
    }

    suspend fun vote(num: String, token:String) {
        val networkInfo = cm!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            val jsonObject = JSONObject()
            jsonObject.put("roomName", room?.roomName)
            var vote = num
            if (vote=="☕"){
                vote ="☕️"
            }

            jsonObject.put("vote", vote)
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val response = service.vote(token,body)
        }
    }


    suspend fun reportLocation(lat : String, long : String, roomName : String?, token:String) {
        val networkInfo = cm!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            val jsonObject = JSONObject()
            jsonObject.put("lat", lat)
            jsonObject.put("long", long)
            jsonObject.put("roomName", roomName)
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val response = service.reportLocation(token,body)
        }
    }

    suspend fun getResult(token:String): MutableList<Members>{
        val networkInfo = cm!!.activeNetworkInfo
        var members = mutableListOf<Members>()
        if (networkInfo != null && networkInfo.isConnected) {
            if (room?.roomName != null){
                val response = service.getResult(token, room?.roomName)
                if (response.code()==200){
                    val gson = Gson()
                    val aux= gson.fromJson(response.body()?.string(), LobbyCredentials::class.java)
                    if (aux.result!=null){
                        members = aux.result as MutableList<Members>
                    }
                }
            }

        }
        return members
    }

    suspend fun getCreateOffline(token: String){
        val LobbyToCreate = lobbyDao.getCreatedOffline()
        val LobbyToDelete = lobbyDao.getDeleteOffline()
        LobbyToCreate.forEach{
            this.createRoom(token, it.name!!, it.password!!, it.deck?.name_deck!!, it.deck?.toDeckCredentials()!!.cards)
        }
        LobbyToDelete.forEach{
            this.deleteRoom(token,it.id,it.name)
        }
    }

    suspend fun getRoom(name: String?, token: String):LobbyCredentials {
        val gson = Gson()
        val response = service.getRoom(name,token)
        return gson.fromJson(response.body()?.string(),LobbyCredentials::class.java)


    }
}