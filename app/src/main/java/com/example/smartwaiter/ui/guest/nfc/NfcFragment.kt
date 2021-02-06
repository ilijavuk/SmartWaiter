package com.example.smartwaiter.ui.guest.nfc

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.example.database.UserPreferences
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.StolRepostiory
import com.example.smartwaiter.ui.guest.qr.QrFragmentDirections
import com.example.smartwaiter.ui.guest.qr.QrModelFactory
import com.example.smartwaiter.ui.guest.qr.QrViewModel
import com.example.smartwaiter.util.handleApiError
import hr.foi.air.webservice.util.Resource
import kotlinx.coroutines.launch

class NfcFragment : Fragment(R.layout.fragment_nfc) {

    private lateinit var viewModel: NfcViewModel
    private lateinit var repostiory: StolRepostiory
    private lateinit var viewModelFactory: NfcModelFactory
    private lateinit var userPreferences: UserPreferences
    var hash=""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var hash = arguments?.getString("hash").toString()

        return inflater.inflate(R.layout.fragment_meni, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userPreferences = UserPreferences(requireContext())

        val activity = requireActivity()

            activity.runOnUiThread {
                Log.d("NFC", hash)

                load(hash)
               // val action = QrFragmentDirections.actionQrFragmentToMenuGuestFragment2(it.text.toInt())
                //findNavController().navigate(action)

                // val action = QrFragmentDirections.actionQrFragmentToHomeFragment()
                // findNavController().navigate(action)
            }
    }




    fun load(hash : String){
        Log.d("NFC", "Nesto2")
        repostiory= StolRepostiory(userPreferences)
        viewModelFactory=
            NfcModelFactory(repostiory)
        viewModel= ViewModelProvider(this, viewModelFactory).get(NfcViewModel::class.java)
        decodeFromWeb(hash)
        viewModel.myResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d("REEEEEEEEEEEEE", response.value.toString())

                    lifecycleScope.launch {
                        viewModel.saveActiveRestaurant(response.value[0].lokal_id)
                    }

                   // val action = NfcFragmentDirections.actionNfcFragmentToMenuGuestFragment2()
                 //   findNavController().navigate(action)

                }
                is Resource.Loading -> { }
                is Resource.Failure -> {
                    handleApiError(response) { decodeFromWeb(hash) }

                }
            }
        }

    }

    fun decodeFromWeb(hash: String){
        viewModel.getTableByHash("Stol", "select", hash)
    }

}