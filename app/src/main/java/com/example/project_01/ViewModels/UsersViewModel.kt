package com.example.project_01.ViewModels

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project_01.Database.Database
import com.example.project_01.Deserializers.UserCredentials
import com.example.project_01.Repositories.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class UsersViewModel(database: Database, application: Application,userRepository: UserRepository):  AndroidViewModel(application) {

    val userRepository = userRepository
    val app = application
    lateinit var userToken:String
    lateinit var userId:String
    val database = database

    val sharedPreferences = app?.getSharedPreferences("user_Token", Context.MODE_PRIVATE)


    fun SignUp(username:String, name:String, password:String, view:View, context: Context?){
        viewModelScope.launch {
            val jsonObject = JSONObject()
            jsonObject.put("username", username)
            jsonObject.put("name", name)
            jsonObject.put("password", password)
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val reponse = userRepository.SignUp(body,name,password,view)
            if(reponse!=""){
                val gson = Gson()

                val result = gson.fromJson(reponse, UserCredentials::class.java)

                userToken = result.token
                userId = result.user_id
                sharedPreferences?.edit()?.apply{
                    putString("user_Token",userToken)
                }?.apply()
            }
            else{
                Toast.makeText(app.applicationContext,"Sign up failed", Toast.LENGTH_LONG).show()
            }

        }

    }
    fun LogIn(username: String, password: String, view:View, context : Context?) {
        viewModelScope.launch() {
                val jsonObject = JSONObject()
                jsonObject.put("username", username)
                jsonObject.put("password", password)
                val body = jsonObject.toString()
                    .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

                val reponse = userRepository.LogIn(body, view, username, password)
                if (reponse != "") {
                    val gson = Gson()
                    val result = gson.fromJson(reponse, UserCredentials::class.java)
                    userToken = result.token
                    userId = result.user_id
                    sharedPreferences?.edit()?.apply {
                        putString("user_Token", userToken)
                    }?.apply()
                } else {
                    Toast.makeText(
                        app.applicationContext,
                        "Sign in failed",
                        Toast.LENGTH_LONG
                    ).show()
                }


        }
    }




        fun clearDatabase() {
            viewModelScope.launch (Dispatchers.IO){
                database.clearAllTables()
            }

        }
    }
