package com.example.project_01

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)


        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)

        val sharedPreferences = this.getSharedPreferences("logged_user", Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString("Username", null)
        val password = sharedPreferences?.getString("Password", null)

        if (username != null && password != null) {
            graph.startDestination = R.id.mainFragment
            navHostFragment.navController.graph = graph
        }

        val TIEMPO:Long = 5000
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                cada_5_seg()
                handler.postDelayed(this, TIEMPO)
            }
        }, TIEMPO)
    }
    fun cada_5_seg() {
        Toast.makeText(applicationContext, "5 SEGUNDOS", Toast.LENGTH_SHORT).show()
    }
}