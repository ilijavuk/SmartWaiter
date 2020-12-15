package com.example.smartwaiter.ui.guest.menu_guest

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.Add_mealRepository
import hr.foi.air.webservice.model.Meal


class MealGuestViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.menu_list_item_guest, parent, false)), ViewModelStoreOwner {
    var Name: TextView? = null
    private var Description: TextView? = null
    private var Price: TextView? = null
    private var Image: ImageView? = null
    var OrderBtn: Button? = null
    private var Manager: LinearLayout? = null
    private var Customer: LinearLayout? = null
    private var context: Context
    private var viewModel: MenuGuestViewModel


    init {
        Name = itemView.findViewById(R.id.textViewMealName)
        Price = itemView.findViewById(R.id.textViewMealPrice)
        Description = itemView.findViewById(R.id.textViewMealDescription)
        Image = itemView.findViewById(R.id.imageViewMenu)
        OrderBtn = itemView.findViewById(R.id.btnOrderMeal)
        Manager = itemView.findViewById(R.id.managerOptions)
        Customer = itemView.findViewById(R.id.customerOptions)
        context = Image?.getContext()!!

        val repository = Add_mealRepository()
        val viewModelFactory = MenuGuestModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MenuGuestViewModel::class.java)
    }

    fun bind(meal: Meal) {

        Name?.tag = meal.id_stavka
        Name?.text = meal.naziv
        Price?.text = meal.cijena
        Description?.text = meal.opis
        Image?.let {
            Glide.with(context)
                .load(meal.slika_path)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(it)
        };
        if (meal.aktivno == 0){
            OrderBtn?.isEnabled = false
        }
        else{

            OrderBtn?.isEnabled = true
        }
    }

    override fun getViewModelStore(): ViewModelStore {
        return ViewModelStore();
    }

}