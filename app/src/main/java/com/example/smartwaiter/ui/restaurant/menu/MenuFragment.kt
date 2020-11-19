package com.example.smartwaiter.ui.restaurant.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.AddRestaurantRepository
import com.example.smartwaiter.repository.Add_mealRepository
import com.example.smartwaiter.ui.restaurant.restaurant_list.RestaurantListModelFactory
import com.example.smartwaiter.ui.restaurant.restaurant_list.RestaurantListViewModel
import hr.foi.air.webservice.model.Meal

class MenuFragment : Fragment(R.layout.fragment_meni) {

    private lateinit var viewModel: MenuViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repository = Add_mealRepository()
        val viewModelFactory = MenuModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MenuViewModel::class.java)

        viewModel.getMeal(table="Stavka_jelovnika", method = "select", "1")

        viewModel.myResponse.observe(viewLifecycleOwner, Observer {
            val response = it.body()
            if (response != null) {
                for(meal in response){
                    Log.d("jelo",meal.naziv)
                }
            }
        })
    }
}