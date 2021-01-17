package com.example.smartwaiter.ui.waiter.tablelist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.smartwaiter.ui.auth.register.RegisterFragmentDirections
import com.example.smartwaiter.ui.waiter.tableorder.TableOrderFragment
import hr.foi.air.webservice.model.Order
import hr.foi.air.webservice.model.TableOrder
import java.security.AccessController.getContext

class TableOrderRecyclerAdapter(private val list: List<Order>, fragment: TableOrderFragment)
    : RecyclerView.Adapter<TableOrderViewHolder>(){
    val myFragment=fragment





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableOrderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TableOrderViewHolder(inflater, parent, myFragment)

    }

    override fun onBindViewHolder(holder: TableOrderViewHolder, position: Int) {
        val m: Order = list[position]
        holder.bind(m)

        /*if(m.broj_osoba != 0){
            holder.Card?.setOnClickListener{
                myFragment.callTable(m.id_stol)
            }
        }*/


    }
    override fun getItemCount(): Int = list.size
}