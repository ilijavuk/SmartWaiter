package com.example.smartwaiter.ui.guest.menu_guest

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.smartwaiter.R
import hr.foi.air.webservice.model.Tag

class TagGuestListAdapter(private val list: List<Tag>, fragment: MenuGuestFragment)
: RecyclerView.Adapter<TagGuestViewHolder>() {
    val myFragment=fragment

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagGuestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TagGuestViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TagGuestViewHolder, position: Int) {
        val movie: Tag = list[position]

        holder.bind(movie)
        holder.Name?.setOnClickListener(object : View.OnClickListener {
            var id_tag = holder.Name?.getTag().toString()

            @SuppressLint("ResourceAsColor")
            override fun onClick(v: View?) {
                if(id_tag == "-1"){
                    myFragment.load()
                }
                else {
                    myFragment.loadMenuByTag(id_tag)
                }
                holder.Name?.setBackgroundResource(R.drawable.dot_white)
                var context: FragmentActivity? = myFragment.getActivityContext()
                if(context != null){
                    holder.Name?.setTextColor(ContextCompat.getColor(context, R.color.dark_red))
                }
            }


        })

    }

    override fun getItemCount(): Int = list.size
}