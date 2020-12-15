package com.example.smartwaiter.ui.guest.menu_guest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.webservice.model.Meal


class MealGuestListAdapter(private val list: List<Meal>, fragment: MenuGuestFragment)
    : RecyclerView.Adapter<MealGuestViewHolder>() {
    val myFragment=fragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealGuestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MealGuestViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MealGuestViewHolder, position: Int) {
        val movie: Meal = list[position]

        holder.bind(movie)

        holder.OrderBtn?.setOnClickListener(object : View.OnClickListener {
            var mealId = holder.Name?.getTag().toString()
            override fun onClick(v: View?) {
                myFragment.callOrderMeal(mealId)
            }

        })
    }

    override fun getItemCount(): Int = list.size

}