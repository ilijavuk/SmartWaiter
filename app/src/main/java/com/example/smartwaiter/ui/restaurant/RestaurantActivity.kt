package com.example.smartwaiter.ui.restaurant

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.database.UserPreferences
import com.example.smartwaiter.R
import kotlinx.android.synthetic.main.activity_restoran.*

class RestaurantActivity : AppCompatActivity() {

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restoran)

        setUpNavigation()

        bottom_nav.setupWithNavController(navController)
        //visibilityNavElements(navController)
        if (intent != null) {
            processIntent(intent)
        }
    }

    private fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_restaurant) as NavHostFragment
        navController = navHostFragment.findNavController()
    }

    /*private fun visibilityNavElements(navController: NavController) {
      navController.addOnDestinationChangedListener { _, destination, _ ->
          when (destination.id) {
              R.id.homeFragment,
              R.id.registerFragment -> bottom_nav?.visibility = View.GONE
              else -> bottom_nav?.visibility = View.VISIBLE
          }
      }
  }*/
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
                Log.d("URI detected", ndefRecord.toUri().toString())
            } else {
                // Other NFC Tags
                Log.d("Payload", ndefRecord.payload.contentToString())
            }
            // ...
        }
    }
}