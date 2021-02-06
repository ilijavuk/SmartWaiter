package com.example.smartwaiter.ui.guest

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.database.UserPreferences
import com.example.smartwaiter.R
import com.example.smartwaiter.ui.auth.MainActivity
import com.example.smartwaiter.ui.guest.nfc.NfcFragment
import com.example.smartwaiter.util.startNewActivity
import com.example.smartwaiter.util.visible
import hr.foi.air.webservice.model.Stol
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
        if (intent != null) {
            processIntent(intent)
        }
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

    //OVO ISPOD TREBA ZA NFC
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            processIntent(intent)
        }
    }

    private fun processIntent(checkIntent: Intent) {
        // Check if intent has the action of a discovered NFC tag
        // with NDEF formatted contents
        if (checkIntent.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            // Retrieve the raw NDEF message from the tag
            val rawMessages = checkIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            var ndefMsg = rawMessages?.get(0) as NdefMessage
            var ndefRecord = ndefMsg.records[0]
            if (ndefRecord.toUri() != null) {
                // Use Android functionality to convert payload to URI

                var hash=ndefRecord.toUri().toString()
                Log.d("URI detected", hash)
                //TODO: napravit nešto s NFC-om lol
                val bundle = Bundle()
                bundle.putString("passedUrl", hash)

                navController.setGraph(navController.graph, bundle)

            } else {
                // Other NFC Tags
                Log.d("Payload", ndefRecord.payload.contentToString())
            }
            // ...
        }
    }

}