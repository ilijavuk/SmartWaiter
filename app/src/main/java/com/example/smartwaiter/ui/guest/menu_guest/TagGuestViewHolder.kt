package com.example.smartwaiter.ui.guest.menu_guest


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.Add_mealRepository
import hr.foi.air.webservice.model.Tag

class TagGuestViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
RecyclerView.ViewHolder(inflater.inflate(R.layout.menu_guest_tag_item, parent, false)),
    ViewModelStoreOwner {
    var Name: TextView? = null
    private var viewModel: MenuGuestViewModel


    init {
        Name = itemView.findViewById(R.id.tagRecyclerText)


        val repository = Add_mealRepository()
        val viewModelFactory = MenuGuestModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MenuGuestViewModel::class.java)
    }

    fun bind(tag: Tag) {
        Name?.text=tag.tag
        Name?.tag=tag.id_tag
    }

    override fun getViewModelStore(): ViewModelStore {
        return ViewModelStore();
    }

}