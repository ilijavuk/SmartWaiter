package com.example.smartwaiter.ui.restaurant.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.smartwaiter.R
import com.example.smartwaiter.ui.auth.login.HomeFragmentDirections
import com.example.smartwaiter.ui.restaurant.meni.MeniFragment

class QrFragment : Fragment(R.layout.fragment_qrscanner) {

    private lateinit var codeScanner: CodeScanner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_qrscanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
        codeScanner = CodeScanner(activity, scannerView)
        codeScanner.decodeCallback = DecodeCallback {
            activity.runOnUiThread {

                //TODO: spojit i smislit qr
                Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
               // val action = QrFragmentDirections.actionQrFragmentToHomeFragment()
               // findNavController().navigate(action)
            }
        }
        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

}