package com.example.project_01.Views.Fragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.project_01.R
import com.example.project_01.ViewModels.CardsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val navHost = childFragmentManager.findFragmentById(R.id.swapping_fragment) as? NavHostFragment
        val navController = navHost?.navController
        val viewModel: CardsViewModel by activityViewModels()
        val bottonNav = view.findViewById<BottomNavigationView>(R.id.swap_fragment_button)

        setHasOptionsMenu(true)
        if (navController != null) {
            bottonNav.setupWithNavController(navController)
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.topmenu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val quitter = NavOptions.Builder().setPopUpTo(R.id.logInFragment,true).build()
        view?.let { Navigation.findNavController(it).navigate(R.id.action_mainFragment_to_logInFragment,null,quitter) }

        return true
    }




}