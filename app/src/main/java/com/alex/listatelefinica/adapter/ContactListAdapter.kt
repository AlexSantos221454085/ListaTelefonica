package com.alex.listatelefinica.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex.listatelefinica.adapter.listener.ContactOnClickListener
import com.alex.listatelefinica.adapter.viewholder.ContactViewHolder
import com.alex.listatelefinica.modelo.ContactModel

class ContactListAdapter(
    private val contactList: List<ContactModel>,
    private val contactOnClickListener: ContactOnClickListener
): RecyclerView.Adapter<ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}