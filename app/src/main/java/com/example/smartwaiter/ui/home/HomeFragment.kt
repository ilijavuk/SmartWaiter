package com.example.smartwaiter.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartwaiter.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener {
             val action = HomeFragmentDirections.actionHomeFragmentToWelcomeFragment()
            findNavController().navigate(action)
        }


        btnRegister.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
        btnAddMeal.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddMealFragment()
            findNavController().navigate(action)
        }
    }
}