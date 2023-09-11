package com.alex.listatelefinica.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alex.listatelefinica.R

class ContactViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.image_contact)
    val textName: TextView = view.findViewById(R.id.text_contact_name)

}