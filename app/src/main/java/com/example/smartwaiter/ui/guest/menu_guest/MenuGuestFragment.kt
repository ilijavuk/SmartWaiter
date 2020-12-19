package com.example.smartwaiter.ui.guest.menu_guest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.Add_mealRepository
import com.example.smartwaiter.util.handleApiError
import com.example.smartwaiter.util.visible
import hr.foi.air.webservice.util.Resource
import kotlinx.android.synthetic.main.fragment_meni_guest.*
class MenuGuestFragment : Fragment(R.layout.fragment_meni_guest) {
    private lateinit var lokal: String

    private lateinit var viewModel: MenuGuestViewModel
    private lateinit var repository: Add_mealRepository
    private lateinit var viewModelFactory: MenuGuestModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_meni_guest, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repository = Add_mealRepository()
        viewModelFactory = MenuGuestModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MenuGuestViewModel::class.java)

        lokal = "1"
            //requireArguments().getInt("restaurant_id").toString()

        load()
        loadTags()
        viewModel.myResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    progressBarMenuGuest.visible(false)
                    if (response != null) {
                        val listMeals = response.value
                        recycleViewMenuGuest.layoutManager = LinearLayoutManager(activity)
                        recycleViewMenuGuest.adapter = MealGuestListAdapter(listMeals, this)
                    }
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
        viewModel.myResponse2.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    progressBarMenuGuest.visible(false)
                    if (response != null) {
                        val listTags = response.value
                        val layoutManager: LinearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                        recyclerViewMenuGuestTags.layoutManager = layoutManager
                        recyclerViewMenuGuestTags.adapter = TagGuestListAdapter(listTags, this)
                    }
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


    }

    fun callOrderMeal(mealId: String){
        val meal = mealId

        //val action = MenuFragmentDirections.actionMeniFragmentToEditMealFragment2(meal)
        //findNavController().navigate(action)
    }
    fun load(){
        viewModel.getMeal(table = "Stavka_jelovnika", method = "select", lokal)
    }
    fun loadTags(){
        viewModel.getAllTags(table = "Tag_stavke", "select")
    }
}