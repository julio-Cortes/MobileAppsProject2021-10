package com.example.project_01.ViewModels

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_01.Deserializers.UserCredentials
import com.example.project_01.Repositories.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class UsersViewModel(application: Application):  AndroidViewModel(application) {
    val userRepository: UserRepository
    lateinit var userToken:String
    lateinit var userId:String
    val app = application

    init {
        userRepository = UserRepository(application)
    }

    fun SignUp(username:String, name:String, password:String, view:View, context: Context?){
        viewModelScope.launch {
            val jsonObject = JSONObject()
            jsonObject.put("username", username)
            jsonObject.put("name", name)
            jsonObject.put("password", password)
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val reponse = userRepository.SignUp(body)
            if(reponse!=""){
                val gson = Gson()

                val result = gson.fromJson(reponse, UserCredentials::class.java)

                userToken = result.token
                userId = result.user_id
            }
            else{
                Toast.makeText(app.applicationContext,"Creacion de usuario fallida", Toast.LENGTH_LONG).show()
            }

        }

    }
    fun LogIn(username: String, password: String, view:View, context : Context?){
        viewModelScope.launch {
            val jsonObject = JSONObject()
            jsonObject.put("username", username)
            jsonObject.put("password", password)
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val reponse = userRepository.LogIn(body, view, username, password)
            if (reponse!= "") {
                val gson = Gson()
                val result = gson.fromJson(reponse, UserCredentials::class.java)
                userToken = result.token
                userId = result.user_id
            }
            else{
                Toast.makeText(app.applicationContext,"Inicio de sesion fallida", Toast.LENGTH_LONG).show()
            }

            //Toast.makeText(app.applicationContext,userToken, Toast.LENGTH_LONG).show()
        }


    }

}