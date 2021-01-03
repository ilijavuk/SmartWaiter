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
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.TableOrderRepository
import hr.foi.air.webservice.model.TableOrder

class TableViewHolder(inflater: LayoutInflater, parent: ViewGroup, fragment: TableListFragment) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.table_list_item, parent, false)), ViewModelStoreOwner {
    val myFragment=fragment

    var Name: TextView? = null
    private var PersonNumber: TextView? = null
    //private var Time: TextView? = null
    var Card: CardView?  = null
    var ConstraintLayout: ConstraintLayout? = null
    var View: View? = null
    var ImageView: ImageView? = null



    private var viewModel: TableListViewModel


    init {
        Name = itemView.findViewById(R.id.table_number)
        PersonNumber = itemView.findViewById(R.id.table_person_number)
        //Time = itemView.findViewById(R.id.table_time)
        Card = itemView.findViewById(R.id.table_item)
        ConstraintLayout = itemView.findViewById(R.id.card_layout)
        View = itemView.findViewById(R.id.verical_line)
        ImageView = itemView.findViewById(R.id.table_icon)

        /*var checkBox_1 = itemView.findViewById(R.id.checkbox_1) as CheckBox
        var checkBox_2 = itemView.findViewById(R.id.checkbox_2) as CheckBox
        var checkBox_3 = itemView.findViewById(R.id.checkbox_3) as CheckBox*/

        val repository = TableOrderRepository()
        val viewModelFactory = TableListViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(TableListViewModel::class.java)
    }

    @SuppressLint("ResourceAsColor")
    fun bind(tableOrder: TableOrder){
        Name?.text = "Table " + tableOrder.id_stol.toString()
        PersonNumber?.text = tableOrder.broj_osoba.toString() + " Person"

        if(tableOrder.rezerviran == 1){
            Card?.isClickable = true
            ConstraintLayout?.setBackgroundResource(R.color.dark_red)
            View?.setBackgroundResource(R.color.back_lightgery)
            Name?.setTextColor(myFragment.getResources().getColor(R.color.back_lightgery))
            PersonNumber?.setTextColor(myFragment.getResources().getColor(R.color.back_lightgery))
            ImageView?.setColorFilter(myFragment.getResources().getColor(R.color.back_lightgery))
        }else if(tableOrder.rezerviran == 2){
            Card?.isClickable = true
            ConstraintLayout?.setBackgroundResource(R.color.facebook_blue)
            View?.setBackgroundResource(R.color.back_lightgery)
            Name?.setTextColor(myFragment.getResources().getColor(R.color.back_lightgery))
            PersonNumber?.setTextColor(myFragment.getResources().getColor(R.color.back_lightgery))
            ImageView?.setColorFilter(myFragment.getResources().getColor(R.color.back_lightgery))
        }else{
            Card?.isClickable = false
        }
    }

    override fun getViewModelStore(): ViewModelStore {
        return  ViewModelStore()
    }

}