package com.example.smartwaiter.ui.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartwaiter.R
import kotlinx.android.synthetic.main.fragment_welcome.*


class WelcomeFragment : Fragment(R.layout.fragment_welcome) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogout.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
             findNavController().navigate(action)
        }
    }
}