package com.example.smartwaiter.ui.guest.HashHandler

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartwaiter.R
import com.example.smartwaiter.ui.guest.qr.QrFragmentDirections
import kotlinx.android.synthetic.main.fragment_hash_method.*


class HashMethodFragment : Fragment(R.layout.fragment_hash_method) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnEnterManualy2.setOnClickListener {
            val action =
                HashMethodFragmentDirections.actionHashHandlerFragmentToFragmentManualEntry3()
            findNavController().navigate(action)

        }
        btnQrScan.setOnClickListener {
            val action =
                HashMethodFragmentDirections.actionHashHandlerFragmentToQrFragment()
            findNavController().navigate(action)

        }
        btnCallMap.setOnClickListener {
            val action =
                HashMethodFragmentDirections.actionHashHandlerFragmentToMapFragment()
            findNavController().navigate(action)

        }

    }


}