package com.example.project_01.ViewModels

import android.app.Activity
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.miniproject03.Retrofit.ServiceBuilder
import com.example.project_01.MainActivity
import com.example.project_01.Models.User
import com.example.project_01.Reftrofit.UserRemoteRepository
import com.example.project_01.Navigator.Navigator
import com.example.project_01.R
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

class UsersViewModel(application: Application):  AndroidViewModel(application) {
    val app = application
    lateinit var currentUser: MutableLiveData<User>
    lateinit var response: Response<ResponseBody>
    private val service: UserRemoteRepository
    lateinit var navigator: Navigator

    init {
        service = ServiceBuilder.getRetrofit().create(UserRemoteRepository::class.java)
    }

    fun goToSignUpFragment(view:View){
        navigator.goToSignUpFragment(view)
    }

    fun SignUp(username:String, name:String, password:String, view:View, context: Context?){
        viewModelScope.launch {
            val jsonObject = JSONObject()
            jsonObject.put("username", username)
            jsonObject.put("name", name)
            jsonObject.put("password", password)
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            response = service.signUp(body)
            if (response.code() == 200){
                navigator.activity.onBackPressed()
            }
            else{
                Toast.makeText(context,"Username alredy taken", Toast.LENGTH_SHORT).show()
            }

        }

    }
    fun LogIn(username: String, password: String, view:View, context : Context?){
        viewModelScope.launch {
            val jsonObject = JSONObject()
            jsonObject.put("username", username)
            jsonObject.put("password", password)
            val body = jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            response = service.logIn(body)
            if (response.code() == 200){
                navigator.goToMainFragment(view)
            }
            else{
                Toast.makeText(context,"Username or password incorrect ", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun setNavigator(activity: MainActivity){
        navigator = Navigator(activity)
    }


}