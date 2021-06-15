package com.example.project_01.Repositories

import android.app.Application
import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.miniproject03.Retrofit.ServiceBuilder
import com.example.project_01.Navigator.Navigator
import com.example.project_01.Reftrofit.UserRemoteRepository
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

class UserRepository(userRemoteRepository: UserRemoteRepository,application: Application, navigator: Navigator) {
    val app = application
    lateinit var response: Response<ResponseBody>
    private val service= userRemoteRepository
    val navigator= navigator



    suspend fun SignUp(body: RequestBody, name: String, password: String, view: View):String? {
        response = service.signUp(body)
        if (response.code() == 200){
            navigator.goToMainFragmentSignUp(view)
            val sharedPreferences = app?.getSharedPreferences("logged_user", Context.MODE_PRIVATE)
            sharedPreferences?.edit()?.apply{
                putString("Username",name)
                putString("Password",password)
            }?.apply()
            return response.body()?.string()
        }
        else{
            Toast.makeText(app.applicationContext,"Username alredy taken", Toast.LENGTH_SHORT).show()
        }
        return ""
    }

    suspend fun LogIn(body: RequestBody, view: View, username: String, password: String) :String?{
        response = service.logIn(body)
        if (response.code() == 200){
            navigator.goToMainFragment(view)
            val sharedPreferences = app?.getSharedPreferences("logged_user", Context.MODE_PRIVATE)
            sharedPreferences?.edit()?.apply{
                putString("Username",username)
                putString("Password",password)
            }?.apply()
            return response.body()?.string()


        }
        else{
            Toast.makeText(app.applicationContext,"Username or password incorrect ", Toast.LENGTH_SHORT).show()
        }
        return ""
    }
    fun LogInFramentToSignUpFragment(view: View){
        navigator.goToSignUpFragment(view)
    }

}