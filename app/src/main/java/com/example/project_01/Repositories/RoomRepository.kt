package com.example.project_01.Repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.miniproject03.Retrofit.ServiceBuilder
import com.example.project_01.Database.Database
import com.example.project_01.Database.LobbyDao
import com.example.project_01.Models.Deck
import com.example.project_01.Models.Lobby
import com.example.project_01.Reftrofit.LobbyRemoteRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class RoomRepository(application: Application) {
    val app = application
    private var rooms :LiveData<MutableList<Lobby>>
    private val database: Database
    private val lobbyDao: LobbyDao
    private val service: LobbyRemoteRepository
    lateinit var response: Response<ResponseBody>
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        database = Room.databaseBuilder(application, Database::class.java, "planning_poker-db").fallbackToDestructiveMigration().build()
        lobbyDao = database.RoomDao()
        rooms = lobbyDao.getAll()
        service = ServiceBuilder.getRetrofit().create(LobbyRemoteRepository::class.java)

    }


    fun createRoom(token: String, name:String, password:String, deck: Deck) : String? {
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
                Toast.makeText(app.applicationContext,"SALA CREADA", Toast.LENGTH_SHORT).show()
                return response.body()?.string()
            }
            else{
                Toast.makeText(app.applicationContext,"SALA NO CREADA", Toast.LENGTH_SHORT).show()
                return ""
            }
            executor.execute{
                //AQUI FALTA AGREGARLE EL DECK
                lobbyDao.insert(Lobby(0,name, password))
            }

        } else {
            // No hay conexión a Internet en este momento
            executor.execute{
                //AQUI FALTA AGREGARLE EL DECK
                lobbyDao.insert(Lobby(0,name, password))
            }
            return ""
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