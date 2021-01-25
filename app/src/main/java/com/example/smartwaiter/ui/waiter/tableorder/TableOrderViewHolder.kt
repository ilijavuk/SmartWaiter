package com.example.smartwaiter.ui.waiter.tablelist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.OrderRepository
import com.example.smartwaiter.repository.OrderRepository2
import com.example.smartwaiter.repository.TableOrderRepository
import com.example.smartwaiter.ui.waiter.tableorder.TableOrderFragment
import hr.foi.air.webservice.model.Meal
import hr.foi.air.webservice.model.Order
import hr.foi.air.webservice.model.Order2
import hr.foi.air.webservice.model.TableOrder

class TableOrderViewHolder(inflater: LayoutInflater, parent: ViewGroup, fragment: TableOrderFragment) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.order_list_item_2, parent, false)), ViewModelStoreOwner {
    val myFragment=fragment
    private var context: Context


    var Title: TextView? = null
    private var OrderCount: TextView? = null
    private var Image: ImageView? = null
    var Card: CardView?  = null
    var ConstraintLayout: ConstraintLayout? = null
    var View: View? = null
    var ImageView: ImageView? = null




    private var viewModel: TableOrderViewModel


    init {
        Title = itemView.findViewById(R.id.title_textView)
        OrderCount = itemView.findViewById(R.id.order_count)
        Image = itemView.findViewById(R.id.order_imageView)
        Card = itemView.findViewById(R.id.row_cardView)
        ConstraintLayout = itemView.findViewById(R.id.card_layout)
        context = Image?.getContext()!!

        /*var checkBox_1 = itemView.findViewById(R.id.checkbox_1) as CheckBox
        var checkBox_2 = itemView.findViewById(R.id.checkbox_2) as CheckBox
        var checkBox_3 = itemView.findViewById(R.id.checkbox_3) as CheckBox*/

        val repository = OrderRepository2()
        val viewModelFactory = TableOrderViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(TableOrderViewModel::class.java)
    }

    @SuppressLint("ResourceAsColor")
    fun bind(order: Order2){
        Title?.text = "Table " + order.naziv.toString()
        OrderCount?.text = order.kolicina.toString() + "x"
        Image?.let {
            Glide.with(context)
                .load(order.slika_path)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(it)
        };

    }

    override fun getViewModelStore(): ViewModelStore {
        return  ViewModelStore()
    }

}