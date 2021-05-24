package com.example.project_01

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)


        val navHostFragment = (supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        val sharedPreferences = this.getSharedPreferences("logged_user", Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString("Username",null)
        val password= sharedPreferences?.getString("Password",null)
        if (username!=null && password != null){
            graph.startDestination = R.id.mainFragment
            navHostFragment.navController.graph = graph
        }



    }



}