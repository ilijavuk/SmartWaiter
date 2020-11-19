package com.example.smartwaiter.ui.restaurant.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartwaiter.R
import com.squareup.picasso.Picasso
import hr.foi.air.webservice.model.Meal

class MealViewHolder (inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.menu_list_item, parent, false)) {
    private var Name: TextView? = null
    private var Description: TextView? = null
    private var Price: TextView? = null
    private var Image: ImageView? = null
    private var context: Context


    init {
        Name = itemView.findViewById(R.id.textViewMealName)
        Price = itemView.findViewById(R.id.textViewMealPrice)
        Description = itemView.findViewById(R.id.textViewMealDescription)
        Image = itemView.findViewById(R.id.imageViewMenu)
        context = Image?.getContext()!!
    }

    fun bind(meal: Meal) {
        Name?.text = meal.naziv
        Price?.text = meal.cijena
        Description?.text = meal.opis
        //Picasso.with(context).load(meal.slika_path).into(Image)
    }

}