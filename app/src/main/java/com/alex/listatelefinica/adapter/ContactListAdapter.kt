package com.alex.listatelefinica.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex.listatelefinica.R
import com.alex.listatelefinica.adapter.listener.ContactOnClickListener
import com.alex.listatelefinica.adapter.viewholder.ContactViewHolder
import com.alex.listatelefinica.modelo.ContactModel

class ContactListAdapter(
    private val contactList: List<ContactModel>,
    private val contactOnClickListener: ContactOnClickListener
) : RecyclerView.Adapter<ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]
        holder.textName.text = contact.name
        if (contact.imageId > 0) {
            holder.image.setImageResource(contact.imageId)
        } else {
            holder.image.setImageResource(R.drawable.profiledefuault)
        }

        holder.itemView.setOnClickListener {
            contactOnClickListener.clickListener(contact)
        }
    }
}