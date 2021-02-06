package com.example.smartwaiter.ui.guest.qr

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.database.UserPreferences
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.StolRepostiory
import com.example.smartwaiter.ui.guest.qr.QrFragmentDirections
import com.example.smartwaiter.ui.waiter.WaiterActivity
import com.example.smartwaiter.util.handleApiError
import com.example.smartwaiter.util.startNewActivity
import com.example.smartwaiter.util.visible
import com.google.common.primitives.UnsignedBytes.toInt
import hr.foi.air.webservice.util.Resource
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_qrscanner.*
import kotlinx.coroutines.launch

class QrFragment : Fragment(R.layout.fragment_qrscanner) {
    private val args: QrFragmentArgs by navArgs()
    private lateinit var viewModel: QrViewModel
    private lateinit var repostiory: StolRepostiory
    private lateinit var viewModelFactory: QrModelFactory
    private lateinit var codeScanner: CodeScanner
    private lateinit var userPreferences: UserPreferences

    val qrPermissionGranted : MutableLiveData<Boolean> =  MutableLiveData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        qrPermissionGranted.value=false
        val requestPermissionQr =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                // Do something if permission granted
                if (isGranted) {
                    qrPermissionGranted.value = true

                }
                else {

                }
            }
        requestPermissionQr.launch(Manifest.permission.CAMERA)

        return inflater.inflate(R.layout.fragment_qrscanner, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnCallMap.setOnClickListener {
            val action =
                QrFragmentDirections.actionQrFragmentToMapFragment()
            findNavController().navigate(action)

        }
        userPreferences = UserPreferences(requireContext())
        Log.d("Skeniran", args.passedUrl.toString())
        activity?.let {
            qrPermissionGranted.observe(it, Observer {

                if(qrPermissionGranted.value==true) {
                    if(args.passedUrl==null){
                        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
                        val activity = requireActivity()
                        codeScanner = CodeScanner(activity, scannerView)
                        codeScanner.decodeCallback = DecodeCallback {
                            activity.runOnUiThread {

                                var tableHash=it.text.removePrefix("https://smartwaiter.app/app.php?")
                                load(tableHash)
                                //val action = QrFragmentDirections.actionQrFragmentToMenuGuestFragment2(it.text.toInt())
                                //findNavController().navigate(action)
                                Toast.makeText(activity, tableHash, Toast.LENGTH_LONG).show()
                                // val action = QrFragmentDirections.actionQrFragmentToHomeFragment()
                                // findNavController().navigate(action)
                            }
                        }
                        scannerView.setOnClickListener {
                            codeScanner.startPreview()
                        }
                    }
                    else {
                        var tableHash=args.passedUrl.toString().removePrefix("https://smartwaiter.app/app.php?")
                        load(tableHash)
                    }

                }

            })
        }

        if(qrPermissionGranted.value==true) {
            if(args.passedUrl==null){
                val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
                val activity = requireActivity()
                codeScanner = CodeScanner(activity, scannerView)
                codeScanner.decodeCallback = DecodeCallback {
                    activity.runOnUiThread {

                        var tableHash=it.text.removePrefix("https://smartwaiter.app/app.php?")
                        load(tableHash)
                        //val action = QrFragmentDirections.actionQrFragmentToMenuGuestFragment2(it.text.toInt())
                        //findNavController().navigate(action)
                        Toast.makeText(activity, tableHash, Toast.LENGTH_LONG).show()
                        // val action = QrFragmentDirections.actionQrFragmentToHomeFragment()
                        // findNavController().navigate(action)
                    }
                }
                scannerView.setOnClickListener {
                    codeScanner.startPreview()
                }
            }
            else {
                var tableHash=args.passedUrl.toString().removePrefix("https://smartwaiter.app/app.php?")
                load(tableHash)
            }

        }




    }

    override fun onResume() {
        super.onResume()
        if(args.passedUrl==null){
        activity?.let {
            qrPermissionGranted.observe(it, Observer {

                if(qrPermissionGranted.value==true) {
                    codeScanner.startPreview()

                }

            })
        }

        if(qrPermissionGranted.value==true) {
            codeScanner.startPreview()
        }

        }
    }

    override fun onPause() {
        if(args.passedUrl==null){
        activity?.let {
            qrPermissionGranted.observe(it, Observer {

                if(qrPermissionGranted.value==true) {

                    codeScanner.releaseResources()
                }

            })
        }

        if(qrPermissionGranted.value==true) {
            codeScanner.releaseResources()
        }

        }
        super.onPause()
    }

    public fun load(hash : String){
        repostiory= StolRepostiory(userPreferences)
        viewModelFactory=
            QrModelFactory(repostiory)
        viewModel= ViewModelProvider(this, viewModelFactory).get(QrViewModel::class.java)
        decodeFromWeb(hash)
        viewModel.myResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d("REEEEEEEEEEEEE", response.value.toString())

                    lifecycleScope.launch {
                        viewModel.saveActiveRestaurant(response.value[0].lokal_id)
                    }

                    val action =
                        QrFragmentDirections.actionQrFragmentToMenuGuestFragment()
                    findNavController().navigate(action)

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