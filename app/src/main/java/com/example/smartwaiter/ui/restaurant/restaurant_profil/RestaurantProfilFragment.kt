package com.example.smartwaiter.ui.restaurant.restaurant_profil

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.database.UserPreferences
import com.example.smartwaiter.ui.auth.MainActivity
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.RestaurantProfilRepository
import com.example.smartwaiter.util.startNewActivity
import kotlinx.android.synthetic.main.fragment_restaurant_profil.*
import kotlinx.coroutines.launch

class RestaurantProfilFragment  : Fragment(R.layout.fragment_restaurant_profil){

    private lateinit var userPreferences: UserPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreferences = UserPreferences(requireContext())
        val repository = RestaurantProfilRepository(userPreferences)

        btnLogout.setOnClickListener {
           lifecycleScope.launch {
               repository.logout()
               requireActivity().startNewActivity(MainActivity::class.java)
           }
        }
    }
}