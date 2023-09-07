package com.alex.listatelefinica.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.alex.listatelefinica.database.DBHelper
import com.alex.listatelefinica.databinding.ActivityMainBinding
import com.alex.listatelefinica.modelo.ContactModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactList: ArrayList<ContactModel>
    private lateinit var adapter: ArrayAdapter<ContactModel>
    private lateinit var result: ActivityResultLauncher<Intent>
    private lateinit var dbhelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbhelper = DBHelper(this)

        val sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)
        loadList()
        binding.btnLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("username", "")
            editor.apply()
            finish()
        }


        binding.listViewContacts.setOnItemClickListener { _, _, position, _ ->
            /*Toast.makeText(applicationContext, contactList[position].name, Toast.LENGTH_SHORT)
                .show()*/
            val intent = Intent(applicationContext, ContactDetailMainActivity::class.java)
            intent.putExtra("id", contactList[position].id)
            startActivity(intent)
        }

        binding.btnAdd.setOnClickListener {
            result.launch(Intent(applicationContext, NewContactActivity::class.java))
        }

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                loadList()
            } else if (it.data != null && it.resultCode == 0) {
                Toast.makeText(applicationContext, "Operation Canceled", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun loadList() {
        contactList = dbhelper.getAllContact()

        adapter =
            ArrayAdapter(
                applicationContext,
                android.R.layout.simple_list_item_1,
                contactList
            )

        binding.listViewContacts.adapter = adapter
    }
}



