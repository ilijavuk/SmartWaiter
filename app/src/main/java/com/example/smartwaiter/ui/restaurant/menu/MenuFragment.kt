package com.example.smartwaiter.ui.restaurant.menu

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.AddRestaurantRepository
import com.example.smartwaiter.repository.Add_mealRepository
import com.example.smartwaiter.ui.restaurant.restaurant_list.RestaurantListModelFactory
import com.example.smartwaiter.ui.restaurant.restaurant_list.RestaurantListViewModel
import hr.foi.air.webservice.model.Meal
import kotlinx.android.synthetic.main.fragment_meni.*
import kotlinx.android.synthetic.main.menu_list_item.*

class MenuFragment : Fragment(R.layout.fragment_meni) {
    private var lokal: String = "1";

    private lateinit var viewModel: MenuViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_meni, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repository = Add_mealRepository()
        val viewModelFactory = MenuModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MenuViewModel::class.java)

        viewModel.getMeal(table="Stavka_jelovnika", method = "select", lokal)

        viewModel.myResponse.observe(viewLifecycleOwner, Observer {
            val response = it.body()
            if (response != null) {
                recycleViewMenu.layoutManager = LinearLayoutManager(activity)
                recycleViewMenu.adapter = MealListAdapter(response)

            }
        })

    }



}