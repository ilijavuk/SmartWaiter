package com.example.smartwaiter.ui.restaurant.editMeal

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.Add_mealRepository
import kotlinx.android.synthetic.main.fragment_add_meal.*
import kotlinx.android.synthetic.main.fragment_edit_meal.*
import kotlinx.android.synthetic.main.menu_list_item.*


class EditMealFragment: Fragment(R.layout.fragment_edit_meal) {

    private lateinit var viewModel: EditMealViewModel
    private var mealId : String = "4"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val repository = Add_mealRepository()
        val viewModelFactory = EditMealModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditMealViewModel::class.java)

        viewModel.getMealById("Stavka_jelovnika","select", mealId)

        viewModel.myResponse.observe(viewLifecycleOwner, Observer {
            val response = it.body()
            if (response != null) {
                for(m in response){
                    textMealNameEdit.setText(m.naziv)
                    textMealDescriptionEdit.setText(m.opis)
                    textMealPriceEdit.setText(m.cijena)
                    imageViewMealEdit.let {
                        Glide.with(this)
                            .load(m.slika_path)
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .into(it)
                    };
                }

            }
        })

        //Log.d("getmeal",viewModel.myResponse.toString())
    }
}