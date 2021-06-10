package com.example.project_01.Navigator

import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.example.project_01.MainActivity
import com.example.project_01.R
import com.example.project_01.Views.Fragments.CardsFragmentDirections
import com.example.project_01.Views.Fragments.LobbysFragmentDirections

class Navigator(val activity: MainActivity) {



    fun goToMainFragment(view : View){
        view?.let { Navigation.findNavController(it).navigate(R.id.action_logInFragment_to_mainFragment) }
    }
    fun goToSignUpFragment(view: View){
        view?.let { Navigation.findNavController(it).navigate(R.id.action_logInFragment_to_signUpFragment2) }
    }
    fun goToCreateLobbyFragment(view:View){
        val action = LobbysFragmentDirections.actionRoomsFragmentToCreateRoomFragment()
        view?.let { Navigation.findNavController(it).navigate(action) }
    }

    fun goToCardFragmentToSelectedCard(view: View, num:Any) {
        val action = CardsFragmentDirections.actionCardsFragmentToSelectedCard(num as String)
        view?.let { Navigation.findNavController(it).navigate(action) }
    }
    fun goToJoinLobbyFragment(view:View){
        val action = LobbysFragmentDirections.actionRoomsFragmentToJoinLobbyFragment()
        view?.let { Navigation.findNavController(it).navigate(action) }
    }


}