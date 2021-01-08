package com.example.smartwaiter.ui.guest.order

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.database.UserPreferences
import com.example.database.db.SMDatabase
import com.example.database.db.models.OrderedMeal
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.OrderRepository
import hr.foi.air.webservice.model.Order
import kotlinx.android.synthetic.main.fragment_menu_details.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MenuDetailsFragment : Fragment(R.layout.fragment_menu_details) {

    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: OrderViewModel
    private val args: MenuDetailsFragmentArgs by navArgs()
    private var price: Float? = null
    private var user = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreferences = UserPreferences(requireContext())
        val repository = OrderRepository(SMDatabase(requireActivity()),userPreferences)
        val viewModelFactory = OrderViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(OrderViewModel::class.java)

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

        userPreferences.authToken.asLiveData().observe(viewLifecycleOwner, {
            it?.let {
                user = it.toInt()
            }
        })

        buttonAddMealToOrder.setOnClickListener {
            val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
            val currentDateAndTime: String = simpleDateFormat.format(Date())
            val order = Order(user, 6, 1, currentDateAndTime, args.meal.id_stavka.toInt(), multiplier)
            Log.d("Order", order.toString())
            val orderedMeal = OrderedMeal(args.meal, order)
            viewModel.saveOrderedMeal(orderedMeal)
            viewModel.saveOrderBucket(true)
            val action = MenuDetailsFragmentDirections.actionMenuDetailsFragmentToMenuGuestFragment()
            findNavController().navigate(action)
        }
    }

    private fun fillContent(){
        textViewMealTitle.text = args.meal.naziv
        textViewMealDesc.text = args.meal.opis
        buttonAddMealToOrder.text = getString(R.string.currency, args.meal.cijena)
        Glide.with(this)
            .load(args.meal.slika_path)
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(imageViewDetailMeal)
    }

    private fun setPrice(result: Float, multiplier: Int) {
        val sum = result * multiplier
        val sumRoundDec = String.format("%.2f", sum)
        buttonAddMealToOrder.text = getString(R.string.currency, sumRoundDec)
        textViewMealCount.text = getString(R.string.multiplier, multiplier.toString())
    }

}