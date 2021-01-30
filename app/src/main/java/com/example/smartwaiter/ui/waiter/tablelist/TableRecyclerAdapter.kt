package com.example.smartwaiter.ui.waiter.tablelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.foi.air.webservice.model.TableOrder

class TableRecyclerAdapter(private val list: List<TableOrder>, fragment: TableListFragment)
    : RecyclerView.Adapter<TableViewHolder>(){
    val myFragment=fragment





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TableViewHolder(inflater, parent, myFragment)

    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        val m: TableOrder = list[position]
        holder.bind(m)

        if(m.broj_osoba != 0){
            holder.Card?.setOnClickListener{
                myFragment.callTable(m.id_stol, m.rezerviran)
            }
        }


    }
    override fun getItemCount(): Int = list.size
}