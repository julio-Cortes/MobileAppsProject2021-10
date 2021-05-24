package com.example.project_01.Views.Fragments


import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.project_01.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val navHost = childFragmentManager.findFragmentById(R.id.swapping_fragment) as? NavHostFragment
        val navController = navHost?.navController
        val bottonNav = view.findViewById<BottomNavigationView>(R.id.swap_fragment_button)

        setHasOptionsMenu(true)
        if (navController != null) {
            bottonNav.setupWithNavController(navController)
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                }
            }
            )

        return view




    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.topmenu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sharedPreferences = activity?.getSharedPreferences("logged_user", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.apply{
            putString("Username",null)
            putString("Password",null)

        }?.apply()

        val quitter = NavOptions.Builder().setPopUpTo(R.id.logInFragment,true).build()
        view?.let { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_logInFragment,null,quitter) }

        return true
    }

}