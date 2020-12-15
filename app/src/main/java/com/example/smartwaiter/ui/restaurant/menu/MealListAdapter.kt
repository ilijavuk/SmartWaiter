package com.example.smartwaiter.ui.restaurant.menu


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.webservice.model.Meal


class MealListAdapter(private val list: List<Meal>, fragment: MenuFragment)
    : RecyclerView.Adapter<MealViewHolder>() {
    val myFragment=fragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MealViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val movie: Meal = list[position]

        holder.bind(movie)

        holder.EditMealBtn?.setOnClickListener(object : View.OnClickListener {
            var mealId = holder.Name?.getTag().toString()
            override fun onClick(v: View?) {
                myFragment.callEditMeal(mealId)
            }

        })

    }

    override fun getItemCount(): Int = list.size

}