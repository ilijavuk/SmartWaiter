package com.example.smartwaiter.ui.guest.menu_guest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.webservice.model.Tag

class TagGuestListAdapter(private val list: List<Tag>, fragment: MenuGuestFragment)
: RecyclerView.Adapter<TagGuestViewHolder>() {
    val myFragment=fragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagGuestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TagGuestViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TagGuestViewHolder, position: Int) {
        val movie: Tag = list[position]

        holder.bind(movie)

    }

    override fun getItemCount(): Int = list.size
}