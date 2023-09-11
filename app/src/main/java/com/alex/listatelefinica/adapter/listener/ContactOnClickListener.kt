package com.alex.listatelefinica.adapter.listener

import com.alex.listatelefinica.modelo.ContactModel

class ContactOnClickListener(val clickListener: (contact: ContactModel) -> Unit) {
    fun onClick(contact: ContactModel) = clickListener

}