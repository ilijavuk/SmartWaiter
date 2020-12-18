package com.example.smartwaiter.ui.guest.menuguest

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
        val meal: Meal = list[position]

        holder.bind(meal)

        holder.OrderBtn?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                myFragment.callOrderMeal(meal)
            }

        })
    }

    override fun getItemCount(): Int = list.size

}