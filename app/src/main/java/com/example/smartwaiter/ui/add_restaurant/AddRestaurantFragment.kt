package com.example.smartwaiter.ui.add_restaurant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.AddRestaurantRepository
import kotlinx.android.synthetic.main.fragment_add_restaurant.*

class AddRestaurantFragment : Fragment(R.layout.fragment_add_restaurant) {

    private lateinit var viewModel: AddRestaurantViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = AddRestaurantRepository()
        val viewModelFactory = AddRestaurantModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddRestaurantViewModel::class.java)

        viewModel.myResponse.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(context, "Adding failed", Toast.LENGTH_SHORT).show()

            }
        })

        btnConfirm.setOnClickListener {
            val restaurantName = editTextRestaurantName.text.toString().trim()
            val address = editTextAddress.text.toString().trim()
            val GPS_Longitude = editTextLongitude.text.toString().trim().toDoubleOrNull()
            val GPS_Latitude = editTextLatitude.text.toString().trim().toDoubleOrNull()
            if(restaurantName != "" && address != "" && GPS_Latitude != null && GPS_Longitude != null)
                viewModel.insertRestoran(table = "Lokal", method = "insert", restaurantName, address, GPS_Longitude, GPS_Latitude)
            else
                Toast.makeText(context, "Please check your inputs", Toast.LENGTH_SHORT).show()
        }
    }
}