package com.example.smartwaiter.ui.restaurant_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.Repository
import hr.foi.air.webservice.model.Restoran
import kotlinx.android.synthetic.main.fragment_restaurant_list.*

class RestaurantList : Fragment(R.layout.fragment_restaurant_list) {
    lateinit var lv: ListView
    lateinit var restaurants: Array<Restoran>
    lateinit var adapter: ArrayAdapter<String>

    private lateinit var viewModel: RestaurantListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository()
        val viewModelFactory = RestaurantListModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RestaurantListViewModel::class.java)

        lv = view.findViewById<ListView>(R.id.restaurant_list_view)

        btnAddRestaurant.setOnClickListener {
            val action = RestaurantListDirections.actionRestaurantListFragmentToAddRestaurant(1)
            findNavController().navigate(action)
        }

        adapter = ArrayAdapter<String>(requireActivity(), R.layout.centered_list_layout)
        viewModel.getRestorani(table="Lokal", method = "select")
        lv.adapter=adapter

        viewModel.myResponse.observe(viewLifecycleOwner, Observer {
            val response = it.body()
            if (response != null) {
                for(restaurant in response){
                    adapter.add(restaurant.toString())
                }
            }
        })
        //lv.setOnItemClickListener { parent, view, position, id ->
        //    val element = adapter.getItem(position) // The item that was clicked
        //    Toast.makeText(context, ""+ element!!.id_lokal, Toast.LENGTH_SHORT).show()
        //}
    }

}