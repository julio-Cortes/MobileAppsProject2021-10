package com.example.project_01

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.service.LocationService
import com.example.project_01.ViewModels.CardsViewModel
import com.example.project_01.Views.Fragments.CardsFragment
import org.koin.android.ext.android.inject

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
        askPermissions()
        LocationService.startLocationService(this)
    }
    private fun askPermissions(){
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),200)
        }
    }
}