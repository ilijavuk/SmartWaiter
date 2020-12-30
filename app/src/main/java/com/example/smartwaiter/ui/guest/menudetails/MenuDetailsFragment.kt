package com.example.smartwaiter.ui.guest.menudetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.database.UserPreferences
import com.example.database.db.SMDatabase
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.OrderRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_menu_details.*

class MenuDetailsFragment : Fragment(R.layout.fragment_menu_details) {

    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: MenuDetailsViewModel
    private val args: MenuDetailsFragmentArgs by navArgs()
    private var price: Float? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreferences = UserPreferences(requireContext())
        val repository = OrderRepository(SMDatabase(requireActivity()))
        val viewModelFactory = MenuDetailsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MenuDetailsViewModel::class.java)

        price = args.meal.cijena.toFloat()
        var multiplier = 1
        fillContent()

        buttonPlus.setOnClickListener {
            multiplier = multiplier + 1
            setPrice(price!!, multiplier)
        }

        buttonMinus.setOnClickListener {
            if (multiplier > 1) {
                multiplier = multiplier - 1
                setPrice(price!!, multiplier)
            }
        }

        buttonAddMealToOrder.setOnClickListener {
            viewModel.saveMeal(args.meal)
            Snackbar.make(view,"Meal added to Bucket", Snackbar.LENGTH_SHORT).show()
            val action = MenuDetailsFragmentDirections.actionMenuDetailsFragmentToMenuGuestFragment(true)
            findNavController().navigate(action)
        }
    }

    private fun fillContent(){
        textViewMealTitle.text = args.meal.naziv
        textViewMealDesc.text = args.meal.opis
        buttonAddMealToOrder.text = getString(R.string.currency, args.meal.cijena)
        Glide.with(this)
            .load(args.meal.slika_path)
            .transition(DrawableTransitionOptions.withCrossFade(1200))
            .into(imageViewDetailMeal)
    }

    private fun setPrice(result: Float, multiplier: Int) {
        val sum = result * multiplier
        val sumRoundDec = String.format("%.2f", sum)
        buttonAddMealToOrder.text = getString(R.string.currency, sumRoundDec)
        textViewMealCount.text = getString(R.string.multiplier, multiplier.toString())
    }

}