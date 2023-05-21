package com.example.labone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class ContentActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)

        // Следим за навигацией
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            // Если попали на список, то делаем нижнюю навигацию видимой
            if (destination.id == R.id.first_fragment) {
                bottomNav.visibility = View.VISIBLE
            }
        }

        navController.addOnDestinationChangedListener(listener)
    }
}