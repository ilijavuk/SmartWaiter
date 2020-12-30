package com.example.smartwaiter.ui.guest.menuguest

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.Add_mealRepository
import com.example.smartwaiter.util.handleApiError
import com.example.smartwaiter.util.visible
import hr.foi.air.webservice.model.Meal
import hr.foi.air.webservice.util.Resource
import kotlinx.android.synthetic.main.fragment_meni_guest.*

class MenuGuestFragment : Fragment(R.layout.fragment_meni_guest) {

    private lateinit var viewModel: MenuGuestViewModel
    private val args: MenuGuestFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)

        val repository = Add_mealRepository()
        val viewModelFactory = MenuGuestModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MenuGuestViewModel::class.java)

        floatingActionButtonBasket.visible(false)

            //requireArguments().getInt("restaurant_id").toString()
        load()

        viewModel.myResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    progressBarMenuGuest.visible(false)
                        val listMeals = response.value
                        recycleViewMenuGuest.layoutManager = LinearLayoutManager(activity)
                        recycleViewMenuGuest.adapter = MealGuestListAdapter(listMeals, this)

                }
                is Resource.Loading -> {
                    progressBarMenuGuest.visible(true)
                }
                is Resource.Failure -> {
                    handleApiError(response) { load() }
                    progressBarMenuGuest.visible(true)
                    Log.d("Response", response.toString())
                }
            }
        })

        floatingActionButtonBasket.setOnClickListener {
            val action = MenuGuestFragmentDirections.actionMenuGuestFragmentToMenuGuestDialogFragment()
            findNavController().navigate(action)
        }

    }

    fun callOrderMeal(meal: Meal){
        val action = MenuGuestFragmentDirections.actionMenuGuestFragmentToMenuDetailsFragment(meal)
        findNavController().navigate(action)
    }


    private fun load(){
        if(args.ordered == true){
            floatingActionButtonBasket.visible(true)
        }

    }
}