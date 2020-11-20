package com.example.smartwaiter.ui.restaurant.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.webservice.model.Meal

class MealListAdapter (private val list: List<Meal>)
    : RecyclerView.Adapter<MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MealViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val movie: Meal = list[position]
        holder.bind(movie)

    }

    override fun getItemCount(): Int = list.size

}