package com.example.project_01.Navigator

import android.view.View
import androidx.navigation.Navigation
import com.example.project_01.MainActivity
import com.example.project_01.R

class Navigator(val activity: MainActivity) {


    fun goToMainFragment(view : View){
        view?.let { Navigation.findNavController(it).navigate(R.id.action_logInFragment_to_mainFragment) }
    }
    fun goToSignUpFragment(view: View){
        view?.let { Navigation.findNavController(it).navigate(R.id.action_logInFragment_to_signUpFragment2) }
    }
}