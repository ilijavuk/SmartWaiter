package com.example.smartwaiter.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.database.db.models.OrderedMeal
import com.example.smartwaiter.databinding.OrderListItemBinding

class OrderDialogAdapter(val clickListener: (OrderedMeal) -> Unit) :
    ListAdapter<OrderedMeal, OrderDialogAdapter.OrderViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding =
            OrderListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, clickListener)

    }

    class OrderViewHolder(private val binding: OrderListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderedMeal: OrderedMeal, clickListener: (OrderedMeal) -> Unit) {
            val price: Float = orderedMeal.meal.cijena.toFloat() * orderedMeal.order.kolicina
            val finalMealPrice = String.format("%.2f", price)
            binding.apply {
                textViewOrderMealTitle.text = orderedMeal.meal.naziv
                textViewOrderMealPrice.text = finalMealPrice + " HRK"
                textViewMultiplier.text = orderedMeal.order.kolicina.toString() + "x"
                Glide
                    .with(itemView)
                    .load(orderedMeal.meal.slika_path)
                    .into(imageViewOrderMeal)

                buttonRemoveMeal.setOnClickListener { clickListener(orderedMeal) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<OrderedMeal>() {
        override fun areItemsTheSame(oldItem: OrderedMeal, newItem: OrderedMeal): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: OrderedMeal, newItem: OrderedMeal): Boolean =
            oldItem == newItem
    }
}