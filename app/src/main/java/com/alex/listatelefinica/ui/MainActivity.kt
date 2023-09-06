package com.alex.listatelefinica.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.alex.listatelefinica.database.DBHelper
import com.alex.listatelefinica.databinding.ActivityMainBinding
import com.alex.listatelefinica.modelo.ContactModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactList: ArrayList<ContactModel>
    private lateinit var adapter: ArrayAdapter<ContactModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbhelper = DBHelper(this)

        val sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)

        binding.btnLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("username", "")
            editor.apply()
            finish()
        }

        contactList = dbhelper.getAllContact()

        adapter =
            ArrayAdapter(
                applicationContext,
                android.R.layout.simple_list_item_1,
                contactList
            )

        binding.listViewContacts.adapter = adapter

        binding.listViewContacts.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(applicationContext, contactList[position].name, Toast.LENGTH_SHORT)
                .show()
        }
    }
}



