package com.example.smartwaiter.ui.guest.nfc

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.example.database.UserPreferences
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.StolRepostiory
import com.example.smartwaiter.ui.guest.qr.QrModelFactory
import com.example.smartwaiter.ui.guest.qr.QrViewModel

class NfcActivity : AppCompatActivity(){

    private lateinit var viewModel: QrViewModel
    private lateinit var repostiory: StolRepostiory
    private lateinit var viewModelFactory: QrModelFactory
    private lateinit var codeScanner: CodeScanner
    private lateinit var userPreferences: UserPreferences
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc)

        if (intent != null) {
            processIntent(intent)
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

                var hash=ndefRecord.toUri().toString().removePrefix("https://smartwaiter.app/app.php?")
                Log.d("URI detected", hash)
                //TODO: napravit nešto s NFC-om lol
                val bundle = Bundle()
                bundle.putString("hash", hash)




            } else {
                // Other NFC Tags
                Log.d("Payload", ndefRecord.payload.contentToString())
            }
            // ...
        }
    }

}