package com.example.smartwaiter.ui.restaurant.menu


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

class TagViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
RecyclerView.ViewHolder(inflater.inflate(R.layout.menu_tag_item, parent, false)),
    ViewModelStoreOwner {
    var Name: TextView? = null
    private var viewModel: MenuViewModel


    init {
        Name = itemView.findViewById(R.id.tagRecyclerTextMeni)


        val repository = Add_mealRepository()
        val viewModelFactory = MenuModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MenuViewModel::class.java)
    }

    fun bind(tag: Tag) {
        Name?.text=tag.tag
        Name?.tag=tag.id_tag
    }

    override fun getViewModelStore(): ViewModelStore {
        return ViewModelStore();
    }

}