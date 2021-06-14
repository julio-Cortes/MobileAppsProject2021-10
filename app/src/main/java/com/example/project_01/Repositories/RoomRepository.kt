package com.example.project_01.Repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.miniproject03.Retrofit.ServiceBuilder
import com.example.project_01.Database.Database
import com.example.project_01.Database.LobbyDao
import com.example.project_01.Deserializers.DecksCredentials
import com.example.project_01.Deserializers.LobbyCredentials
import com.example.project_01.Deserializers.LobbyListCredentials
import com.example.project_01.Models.Deck
import com.example.project_01.Models.Lobby
import com.example.project_01.Reftrofit.LobbyRemoteRepository
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class RoomRepository(application: Application, room:Database, lobbyDao:LobbyDao) {
    val app = application
    private var rooms : LiveData<MutableList<Lobby>>
    private val database: Database
    private val lobbyDao = lobbyDao
    private val service: LobbyRemoteRepository
    lateinit var response: Response<ResponseBody>
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        service = ServiceBuilder.getRetrofit().create(LobbyRemoteRepository::class.java)
        database = room
        rooms = lobbyDao.getAll()
    }


    fun createRoom(token: String, name:String, password:String, deck: DecksCredentials) : LobbyCredentials? {
        var lobby = LobbyCredentials("","", "", "",listOf(), null)
        val cm = app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm!!.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            var gson = Gson()
            val jsonObject = JSONObject()
            jsonObject.put("roomName", name)
            jsonObject.put("password", password)
            jsonObject.put("deck", gson.toJson(deck))
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = service.createRoom(token, body)
            call.enqueue(object : Callback<LobbyCredentials> {
                override fun onResponse(call: Call<LobbyCredentials>, response: Response<LobbyCredentials>) {
                    if (response.code()==200){
                        val body = response.body()
                        if (body != null) {
                            lobby = body
                            val deck_deck = Deck(0,deck.name, deck.cards.toString())
                            lobbyDao.insert(Lobby(0,lobby.roomId,name, password, deck_deck))

                        }
                    }

                }
                override fun onFailure(call: Call<LobbyCredentials>, t: Throwable) {
                }
            }

            )

        }
        else {
            executor.execute{
                val deck_deck = Deck(0,deck.name, deck.cards.toString())
                lobbyDao.insert(Lobby(0,lobby.roomId,name, password, deck_deck))
            }

        }
        return null

    }

    fun joinRoom(token: String, name:String, password:String) : LobbyCredentials? {
        var lobby = LobbyCredentials("","","","",listOf(), null)
        val cm = app.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm!!.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            val jsonObject = JSONObject()
            jsonObject.put("name", name)
            jsonObject.put("password", password)
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val call = service.joinRoom(token, body)
            call.enqueue(object : Callback<LobbyCredentials> {
                override fun onResponse(call: Call<LobbyCredentials>, response: Response<LobbyCredentials>) {
                    val body = response.body()
                    if (body != null) {
                        lobby = body
                    }
                }
                override fun onFailure(call: Call<LobbyCredentials>, t: Throwable) {
                }
            }
            )
            return lobby
        }
        else {
            Toast.makeText(app.applicationContext,"You don´t have internet to joina room", Toast.LENGTH_SHORT).show()
            return null
        }

    }
    fun getLobbyCredentials(token: String) {

        val call = service.getRooms(token)
        call.enqueue(object : Callback<LobbyListCredentials> {
                override fun onResponse(call: Call<LobbyListCredentials>, response: Response<LobbyListCredentials>) {
                    val body = response.body()
                    if (body?.rooms != null) {
                        body?.rooms.forEach {
                            //it me entrega un room_id y name y con esos datos debo hacer getRoom e insertarlo a la
                            //base de datos de lobbydao con lo que me entregue
                            //executor.execute{
                                //lobbyDao.insert(Lobby())
                            //}

                        }
                    }
                }
                override fun onFailure(call: Call<LobbyListCredentials>, t: Throwable) {
                }
            }
        )
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