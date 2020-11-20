package com.example.smartwaiter.ui.restaurant.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.smartwaiter.R
import com.example.smartwaiter.util.visible
import hr.foi.air.webservice.model.Meal

class MealViewHolder (inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.menu_list_item, parent, false)) {
    private var Name: TextView? = null
    private var Description: TextView? = null
    private var Price: TextView? = null
    private var Image: ImageView? = null
    private var Available: Switch? = null
    private var EditMealBtn: Button? = null
    private var OrderBtn: Button? = null
    private var Manager: LinearLayout? = null
    private var Customer: LinearLayout? = null
    private var context: Context


    init {
        Name = itemView.findViewById(R.id.textViewMealName)
        Price = itemView.findViewById(R.id.textViewMealPrice)
        Description = itemView.findViewById(R.id.textViewMealDescription)
        Image = itemView.findViewById(R.id.imageViewMenu)
        Available = itemView.findViewById(R.id.switchAvailable)

        EditMealBtn = itemView.findViewById(R.id.btnEditMeal)
        OrderBtn = itemView.findViewById(R.id.btnOrderMeal)

        Manager = itemView.findViewById(R.id.managerOptions)
        Customer = itemView.findViewById(R.id.customerOptions)

        context = Image?.getContext()!!
    }

    fun bind(meal: Meal) {
        //NAREDBE ZA SKRIVANJE TIPA OPCIJA
        //Manager?.visible(false)
        //Customer?.visible(false)
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
            Available?.isChecked = false
            OrderBtn?.isEnabled = false
        }
        else{

            Available?.isChecked = true
        }
        Available?.setOnCheckedChangeListener { buttonView, isChecked ->
            var test: String = ""
            if (isChecked) {
                OrderBtn?.isEnabled = true
                test =Name?.text.toString() +" ON"
                Toast.makeText(context, test, Toast.LENGTH_SHORT).show()
            } else {
                test =Name?.text.toString() +" OFF"
                OrderBtn?.isEnabled = false
                Toast.makeText(context, test, Toast.LENGTH_SHORT).show()
            }
        }

        EditMealBtn?.setOnClickListener{
            //Ovdje se poziva uređivanje nekog jela
            Toast.makeText(context, Name?.text, Toast.LENGTH_SHORT).show()
        }
        OrderBtn?.setOnClickListener{
            //Ovdje se poziva naručivanje nekog jela
            Toast.makeText(context, Name?.text, Toast.LENGTH_SHORT).show()
        }
    }

}