package com.example.smartwaiter.ui.restaurant.menu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartwaiter.R.color
import com.example.smartwaiter.R.drawable
import hr.foi.air.webservice.model.Tag
import kotlinx.android.synthetic.main.fragment_meni.*
import kotlinx.android.synthetic.main.menu_tag_item.view.*


class TagListAdapter(private val list: List<Tag>, fragment: MenuFragment)
: RecyclerView.Adapter<TagViewHolder>() {
    val myFragment=fragment
    var row_index = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TagViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val movie: Tag = list[position]
        holder.bind(movie)
        var context: FragmentActivity? = myFragment.getActivityContext()
        holder.Name?.setOnClickListener(object : View.OnClickListener {
            var id_tag = holder.Name?.getTag().toString()

            @SuppressLint("ResourceAsColor")
            override fun onClick(v: View?) {
                row_index = position
                if (id_tag == "-1") {
                    myFragment.load()
                } else {
                    myFragment.loadMenuByTag(id_tag)
                }
                notifyDataSetChanged()

            }

        })
        if(row_index==position){
            holder.Name?.setBackgroundResource(drawable.dot_white)
            if (context != null) {
                holder.Name?.setTextColor(ContextCompat.getColor(context, color.dark_red))
            }
        }
        else{
            holder.Name?.setBackgroundResource(drawable.dot)
            if (context != null) {
                holder.Name?.setTextColor(ContextCompat.getColor(context, color.white))
            }
        }

    }

    override fun getItemCount(): Int = list.size
}