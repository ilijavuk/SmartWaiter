package com.example.smartwaiter.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartwaiter.databinding.OrderListItemBinding
import hr.foi.air.webservice.model.Meal

class OrderDialogAdapter(val clickListener: (Meal) -> Unit) : ListAdapter<Meal, OrderDialogAdapter.OrderViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, clickListener)

    }

    class OrderViewHolder(private val binding: OrderListItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(meal: Meal, clickListener: (Meal) -> Unit){
            binding.apply {
                textViewOrderMealTitle.text = meal.naziv
                textViewOrderMealPrice.text = meal.cijena
                Glide
                    .with(itemView)
                    .load(meal.slika_path)
                    .into(imageViewOrderMeal)

                buttonRemoveMeal.setOnClickListener{clickListener(meal)}
            }
        }
    }

    class DiffCallback:DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean =
            oldItem.id_stavka == newItem.id_stavka

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean =
            oldItem == newItem
    }
}