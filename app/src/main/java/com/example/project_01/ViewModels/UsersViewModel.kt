package com.example.project_01.ViewModels

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.miniproject03.Retrofit.ServiceBuilder
import com.example.project_01.Models.User
import com.example.project_01.Reftrofit.UserRemoteRepository
import com.example.project_01.Navigator.Navigator
import com.example.project_01.Repositories.UserRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

class UsersViewModel(application: Application):  AndroidViewModel(application) {
    val userRepository: UserRepository

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
            userRepository.SignUp(body)
        }

    }
    fun LogIn(username: String, password: String, view:View, context : Context?){
        viewModelScope.launch {
            val jsonObject = JSONObject()
            jsonObject.put("username", username)
            jsonObject.put("password", password)
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            userRepository.LogIn(body, view, username, password)
        }


    }

}