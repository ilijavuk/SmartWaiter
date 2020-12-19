package com.example.smartwaiter.ui.restaurant.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.webservice.model.Tag

class TagListAdapter(private val list: List<Tag>, fragment: MenuFragment)
: RecyclerView.Adapter<TagViewHolder>() {
    val myFragment=fragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TagViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val movie: Tag = list[position]

        holder.bind(movie)

    }

    override fun getItemCount(): Int = list.size
}