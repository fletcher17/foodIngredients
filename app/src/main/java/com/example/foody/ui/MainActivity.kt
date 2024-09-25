package com.example.foody.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foody.R
import com.example.foody.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //this is the navHost component
        navController = findNavController(R.id.fragmentContainerView)

        //this is the appBarConfiguration, we need to pass our destinations here.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.recipiesFragment,
                R.id.favoriteRecipiesFragment,
                R.id.foodJokeFragment
            )
        )

        //next, we set up the bottomNavBar with the navController i.e connect the bottom nav bar with the nav host controller
        binding.bottomNavBar.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}