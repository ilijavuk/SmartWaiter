package com.example.smartwaiter.ui.restaurant.menu

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
import com.example.smartwaiter.ui.restaurant.editMeal.EditMealFragment
import hr.foi.air.webservice.model.Meal


class MealViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.menu_list_item, parent, false)), ViewModelStoreOwner {
    var Name: TextView? = null
    private var Description: TextView? = null
    private var Price: TextView? = null
    private var Image: ImageView? = null
    private var Available: Switch? = null
    var EditMealBtn: Button? = null
    private var OrderBtn: Button? = null
    private var Manager: LinearLayout? = null
    private var Customer: LinearLayout? = null
    private var context: Context
    private var viewModel: MenuViewModel


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

        val repository = Add_mealRepository()
        val viewModelFactory = MenuModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MenuViewModel::class.java)
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
                viewModel.setMealAvailability(
                    table = "Stavka_jelovnika",
                    method = "update",
                    mealId = Name?.tag.toString(),
                    available = "1"
                )
                OrderBtn?.isEnabled = true
            } else {
                test =Name?.text.toString() +" OFF"
                viewModel.setMealAvailability(
                    table = "Stavka_jelovnika",
                    method = "update",
                    mealId = Name?.tag.toString(),
                    available = "0"
                )
                OrderBtn?.isEnabled = false
            }
        }

        EditMealBtn?.setOnClickListener{
            //Ovdje se poziva uređivanje nekog jela
            //val action = MenuFragmentDirections.actionMenuFragmentToEditMealFragment()
            //findNavController().navigate(action)
            Toast.makeText(context, Name?.text, Toast.LENGTH_SHORT).show()

        }
        OrderBtn?.setOnClickListener{
            //Ovdje se poziva naručivanje nekog jela
            Toast.makeText(context, Name?.text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getViewModelStore(): ViewModelStore {
        return ViewModelStore();
    }

}