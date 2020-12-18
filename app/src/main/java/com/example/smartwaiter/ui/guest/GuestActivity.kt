package com.example.smartwaiter.ui.guest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.smartwaiter.R
import kotlinx.android.synthetic.main.activity_guest.*

class GuestActivity : AppCompatActivity() {


    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest)

        setUpNavigation();
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_guest) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(toolbarGuest)
        toolbarGuest.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}