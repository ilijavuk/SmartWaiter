package com.example.smartwaiter.ui.guest.menu_guest

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.database.UserPreferences
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.Add_mealRepository
import com.example.smartwaiter.ui.guest.menuguest.MealGuestListAdapter
import com.example.smartwaiter.ui.guest.menuguest.MenuGuestModelFactory
import com.example.smartwaiter.ui.guest.menuguest.MenuGuestViewModel
import com.example.smartwaiter.ui.guest.order.OrderDialogFragment
import com.example.smartwaiter.ui.guest.order.OrderViewModel
import com.example.smartwaiter.util.handleApiError
import com.example.smartwaiter.util.visible
import hr.foi.air.webservice.model.Meal
import hr.foi.air.webservice.model.Tag
import hr.foi.air.webservice.util.Resource
import kotlinx.android.synthetic.main.fragment_meni_guest.*
import kotlinx.coroutines.launch

class MenuGuestFragment : Fragment(R.layout.fragment_meni_guest) {

    private lateinit var lokal: String
    private lateinit var stol: String
    private lateinit var viewModel: MenuGuestViewModel
    private lateinit var repository: Add_mealRepository
    private lateinit var viewModelFactory: MenuGuestModelFactory
    private lateinit var userPreferences: UserPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userPreferences = UserPreferences(requireContext())
        repository = Add_mealRepository()
        viewModelFactory = MenuGuestModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MenuGuestViewModel::class.java)


        progressBarMenuGuest.visible(false)
        updateOrderBucketUI()

        floatingActionButtonBasket.setOnClickListener {
            val action = MenuGuestFragmentDirections.actionMenuGuestFragmentToMenuGuestDialogFragment(lokal)
            findNavController().navigate(action)
        }

        userPreferences.activeRestaurant.asLiveData().observe(viewLifecycleOwner, {
            it?.let {
                lokal = it
                load()
                loadTags()
            }
        })

        viewModel.myResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    progressBarMenuGuest.visible(false)
                    Log.d("tagovi",response.value.toString())
                    val listMeals = response.value
                    recycleViewMenuGuest.layoutManager = LinearLayoutManager(activity)
                    recycleViewMenuGuest.adapter = MealGuestListAdapter(listMeals, this)
                }
                is Resource.Loading -> {
                    progressBarMenuGuest.visible(true)
                }
                is Resource.Failure -> {
                    handleApiError(response) { load() }
                    progressBarMenuGuest.visible(false)
                }
            }
        })
        viewModel.myResponse2.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    progressBarMenuGuest.visible(false)
                    val listTags: MutableList<Tag> = response.value as MutableList<Tag>
                    listTags.add(0, Tag("-1", resources.getString(R.string.all_items)))
                    val layoutManager: LinearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewMenuGuestTags.layoutManager = layoutManager
                    recyclerViewMenuGuestTags.adapter = TagGuestListAdapter(listTags, this)
                    Log.d("tagovi" , response.value.toString())
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

    fun callOrderMeal(meal: Meal){
        val action = MenuGuestFragmentDirections.actionMenuGuestFragmentToMenuDetailsFragment(meal)
        findNavController().navigate(action)
    }

    fun load(){
        viewModel.getMeal(table = "Stavka_jelovnika", method = "select", lokal_id = lokal)
    }
    fun loadTags(){
        viewModel.tagsByRestaurant(method= "tagoviPoRestoranu", lokal_id = lokal)
    }
    fun callMenuByTag(id_tag: String){
        viewModel.menuByTag(method = "meniPoTagu", id_tag=id_tag, lokal_id = lokal)
    }

    fun loadMenuByTag(id_tag: String){
        callMenuByTag(id_tag)
        viewModel.myResponse3.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    val odgovor = response.value
                    recycleViewMenuGuest.layoutManager = LinearLayoutManager(activity)
                    recycleViewMenuGuest.adapter = MealGuestListAdapter(odgovor, this)
                }
                is Resource.Loading -> {

                }
                is Resource.Failure -> {

                    handleApiError(response) { callMenuByTag(id_tag) }
                    Log.d("Response", response.toString())
                }
            }
        })
    }

    fun getActivityContext(): FragmentActivity? {
        return activity
    }

    private fun updateOrderBucketUI(){

        setFragmentResultListener("basket_ui_request", ){_, bundle ->
            val result = bundle.getBoolean("basket_ui_result")
            if (result == true){
                floatingActionButtonBasket.visible(true)
            }
            else{
                floatingActionButtonBasket.visible(false)
            }
        }

        userPreferences.orderBucket.asLiveData().observe(viewLifecycleOwner, {
            it?.let {
                Log.d("FAB", it.toString())
                when(it) {
                    true -> floatingActionButtonBasket.visible(true)
                    false -> floatingActionButtonBasket.visible(false)
                }
            }
        })

    }
}