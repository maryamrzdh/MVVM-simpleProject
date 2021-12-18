package com.example.alibabatask.ui.fragment.first

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alibabatask.R
import com.example.alibabatask.model.Datum

class MyListAdapter(private val activity:Activity,private val mList: MutableList<Datum>) : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]


        if (item.avatar != null) {
            Glide.with(activity)
                .load(item.avatar)
                .into(holder.imageView)
        } else
            holder.imageView.setImageResource(R.drawable.ic_launcher_background)

        holder.name.text = "${item.firstName}  ${item.lastName}"
        holder.email.text=item.email

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.img_user)
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val email: TextView = itemView.findViewById(R.id.tv_email)
    }
}