package com.example.smartwaiter.ui.guest.qr

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.example.database.HashCodeListener
import com.example.database.UserPreferences
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.StolRepostiory
import com.example.smartwaiter.util.handleApiError
import hr.foi.air.webservice.util.Resource
import kotlinx.android.synthetic.main.fragment_qrscanner.*
import kotlinx.coroutines.launch

class QrFragment : Fragment(R.layout.fragment_qrscanner) {
    private val args: QrFragmentArgs by navArgs()
    private lateinit var codeScanner: CodeScanner

    val qrPermissionGranted : MutableLiveData<Boolean> =  MutableLiveData()

    private var ListenerCode: HashCodeListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is HashCodeListener) {
            ListenerCode = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        ListenerCode = null
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        qrPermissionGranted.value=false
        val requestPermissionQr =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->

                if (isGranted) {
                    qrPermissionGranted.value = true

                }
            }
        requestPermissionQr.launch(Manifest.permission.CAMERA)
        return inflater.inflate(R.layout.fragment_qrscanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {




        Log.d("Skeniran", args.passedUrl.toString())

            val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
            val activity = requireActivity()
            codeScanner = CodeScanner(activity, scannerView)
            codeScanner.decodeCallback = DecodeCallback {
                activity.runOnUiThread {

                    val tableHash=it.text.removePrefix("https://smartwaiter.app/app.php?")
                    if(tableHash.length==32){
                        ListenerCode!!.onCodeObtained(tableHash)
                    }
                }
            }
            scannerView.setOnClickListener {
                codeScanner.startPreview()
            }
    }

    override fun onResume() {
        super.onResume()

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

    override fun onPause() {

        if(qrPermissionGranted.value==true) {
            codeScanner.releaseResources()
        }

        super.onPause()
    }







}