package com.example.smartwaiter.ui.guest.raterestaurant

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.database.UserPreferences
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.AuthRepository
import com.example.smartwaiter.repository.RateRestaurantRepository
import com.example.smartwaiter.ui.restaurant.restaurant_list.RestaurantListViewModel
import kotlinx.android.synthetic.main.fragment_rate_restaurant.*

class RateRestaurant : Fragment(R.layout.fragment_rate_restaurant) {
    lateinit var stars: List<ImageView>
    var grade = 0
    lateinit var method: String;
    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: RateRestaurantViewModel
    private var lokal_id = 1;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        stars = listOf(star1, star2, star3, star4, star5)
        stars.forEachIndexed { index, element ->
            element?.setOnClickListener {
                setStars(index)
            }
        }

        userPreferences = UserPreferences(requireContext())
        //lokal_id = requireArguments().getInt("lokal_id").toString()

        val repository = RateRestaurantRepository()
        val viewModelFactory = RateRestaurantViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RateRestaurantViewModel::class.java)

        Log.d("MYTAG", userPreferences.authToken.toString())
        viewModel.getRating(93.toString(), lokal_id.toString())

        viewModel.myResponse.observe(viewLifecycleOwner, {
            val response = it.body()
            if (response != null) {
                method = "update_rating"
                setStars(response[0].ocjena.toInt()-1)
            }
            else{
                method = "insert"
            }
        })

        //restaurant_name.setText(requireArguments().getString("restaurant_name"))
        submit_button.setOnClickListener{
            submitReview()
        }

    }

    private fun setStars(starIndex: Int){
        stars.forEachIndexed { index, element ->
           if(index <= starIndex){
                element.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.star_filled, null))
           }
           else{

               element.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.star_empty, null))
           }
        }
        grade = starIndex+1
    }

    private fun submitReview(){
        viewModel.pushRating(method, 93.toString(), 1.toString(), grade.toString())

    }
}