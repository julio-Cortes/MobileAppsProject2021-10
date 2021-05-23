package com.example.project_01.Repositories

import android.app.Application
import android.util.Log
import androidx.room.Room

import com.example.miniproject03.Retrofit.ServiceBuilder
import com.example.project_01.Database.Database
import com.example.project_01.Models.User
import com.example.project_01.Reftrofit.UserRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {

    private val service: UserRemoteRepository
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        service = ServiceBuilder.getRetrofit().create(UserRemoteRepository::class.java)
    }
    fun SignUp(username:String, name:String, password:String): Response<String>{
        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("name", name)
        jsonObject.put("password", password)
        val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        return service.signUp(body)
    }
    fun LogIn(username: String, password: String): Response<String> {
        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("password", password)
        val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        return service.logIn(body)

    }
}