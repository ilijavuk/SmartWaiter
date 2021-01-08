package com.example.smartwaiter.ui.guest.menu_guest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartwaiter.R
import hr.foi.air.webservice.model.Tag
import kotlinx.android.synthetic.main.fragment_meni_guest.*
import kotlinx.android.synthetic.main.menu_guest_tag_item.view.*

class TagGuestListAdapter(private val list: List<Tag>, fragment: MenuGuestFragment)
: RecyclerView.Adapter<TagGuestViewHolder>() {
    val myFragment=fragment
    var row_index = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagGuestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TagGuestViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TagGuestViewHolder, position: Int) {
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
            holder.Name?.setBackgroundResource(R.drawable.dot_white)
            if (context != null) {
                holder.Name?.setTextColor(ContextCompat.getColor(context, R.color.dark_red))
            }
        }
        else{
            holder.Name?.setBackgroundResource(R.drawable.dot)
            if (context != null) {
                holder.Name?.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        }

    }

    override fun getItemCount(): Int = list.size
}