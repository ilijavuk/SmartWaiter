package com.example.smartwaiter.ui.restaurant.menu

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.database.UserPreferences
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.Add_mealRepository
import com.example.smartwaiter.ui.auth.MainActivity
import com.example.smartwaiter.ui.guest.GuestActivity
import com.example.smartwaiter.ui.restaurant.RestaurantActivity
import com.example.smartwaiter.ui.waiter.WaiterActivity
import com.example.smartwaiter.util.handleApiError
import com.example.smartwaiter.util.startNewActivity
import com.example.smartwaiter.util.visible
import hr.foi.air.webservice.model.Tag
import hr.foi.air.webservice.util.Resource
import kotlinx.android.synthetic.main.fragment_meni.*

class MenuFragment : Fragment(R.layout.fragment_meni) {
    private lateinit var lokal: String

    private lateinit var viewModel: MenuViewModel
    private lateinit var repository: Add_mealRepository
    private lateinit var viewModelFactory: MenuModelFactory
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_meni, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        userPreferences = UserPreferences(requireContext())
        Log.d("restoran","1")
        userPreferences.activeRestaurant.asLiveData().observe(viewLifecycleOwner, {
            it?.let {
                lokal = it
                load()
                loadTags()
            }
        })

        Log.d("restoran","4")
        //lokal = requireArguments().getInt("restaurant_id").toString()
        repository = Add_mealRepository()
        viewModelFactory = MenuModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MenuViewModel::class.java)

        btnCallAddMeal.setOnClickListener{
            findNavController().navigate(MenuFragmentDirections.actionMeniFragmentToAddMealFragment(lokal.toInt()))
        }
        viewModel.myResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    progressBarMenu.visible(false)
                    val odgovor = response.value

                    recycleViewMenu.layoutManager = LinearLayoutManager(activity)
                    recycleViewMenu.adapter = MealListAdapter(odgovor, this)
                }
                is Resource.Loading -> {
                    progressBarMenu.visible(true)
                }
                is Resource.Failure -> {
                    progressBarMenu.visible(true)
                    handleApiError(response) { load() }
                    Log.d("Response", response.toString())
                }
            }
        })

        viewModel.myResponse2.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    val listTags: MutableList<Tag> = response.value as MutableList<Tag>
                    listTags.add(0, Tag("-1", resources.getString(R.string.all_items)))
                    val layoutManager: LinearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    recyclerViewMenuTags.layoutManager = layoutManager
                    recyclerViewMenuTags.adapter = TagListAdapter(listTags, this)
                    Log.d("tagovi" , response.value.toString())
                }
                is Resource.Loading -> {
                }
                is Resource.Failure -> {
                    handleApiError(response) { loadTags() }
                    Log.d("Response", response.toString())
                }
            }
        })

    }

    fun callEditMeal(mealId: String){
        val meal = mealId
        //val action = MenuFragmentDirections.actionMeniFragmentToEditMealFragment2(meal)
        //findNavController().navigate(action)
    }
    fun load(){
        viewModel.getMeal(table = "Stavka_jelovnika", method = "select", lokal)
    }
    fun loadTags(){
        viewModel.tagsByRestaurant(method= "tagoviPoRestoranu", lokal)
        Log.d("tagovi", "pozove se")
    }

    fun callMenuByTag(id_tag: String){
        viewModel.menuByTag(method = "meniPoTagu", id_tag=id_tag, lokal_id = lokal)
    }

    fun loadMenuByTag(id_tag: String){
        callMenuByTag(id_tag)
        viewModel.myResponse3.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    progressBarMenu.visible(false)
                    val odgovor = response.value

                    recycleViewMenu.layoutManager = LinearLayoutManager(activity)
                    recycleViewMenu.adapter = MealListAdapter(odgovor, this)
                }
                is Resource.Loading -> {
                    progressBarMenu.visible(true)
                }
                is Resource.Failure -> {
                    progressBarMenu.visible(true)
                    handleApiError(response) { callMenuByTag(id_tag) }
                    Log.d("Response", response.toString())
                }
            }
        })
    }
    fun getActivityContext(): FragmentActivity? {
        return activity
    }

}