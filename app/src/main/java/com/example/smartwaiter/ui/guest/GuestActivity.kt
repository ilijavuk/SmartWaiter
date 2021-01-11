package com.example.smartwaiter.ui.guest

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.database.UserPreferences
import com.example.smartwaiter.R
import com.example.smartwaiter.ui.auth.MainActivity
import com.example.smartwaiter.util.startNewActivity
import com.example.smartwaiter.util.visible
import kotlinx.android.synthetic.main.activity_guest.*
import kotlinx.coroutines.launch

class GuestActivity : AppCompatActivity() {


    private lateinit var navController: NavController
    private lateinit var preferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest)

        preferences = UserPreferences(this)

        setUpNavigation()
        visibilityNavElements(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.guest_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.homeFragment)
        {
            lifecycleScope.launch {
                preferences.clear()
            }
            startNewActivity(MainActivity::class.java)
        }

        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_guest) as NavHostFragment
        navController = navHostFragment.findNavController()

        val topLevelDestination = setOf(R.id.qrFragment, R.id.menuGuestFragment)
        val appBarConfiguration = AppBarConfiguration(topLevelDestination)

        setSupportActionBar(toolbarGuest)
        toolbarGuest.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.qrFragment,
                R.id.waitMealFragment -> toolbarGuest.visible(false)
                else -> toolbarGuest.visible(true)
            }
        }
    }

}