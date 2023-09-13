package com.alex.listatelefinica.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.alex.listatelefinica.R
import com.alex.listatelefinica.adapter.ContactListAdapter
import com.alex.listatelefinica.adapter.listener.ContactOnClickListener
import com.alex.listatelefinica.database.DBHelper
import com.alex.listatelefinica.databinding.ActivityMainBinding
import com.alex.listatelefinica.modelo.ContactModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactList: List<ContactModel>
   // private lateinit var adapter: ArrayAdapter<ContactModel>
    private lateinit var adapter: ContactListAdapter
    private lateinit var result: ActivityResultLauncher<Intent>
    private lateinit var dbhelper: DBHelper
    private var ascDesc: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbhelper = DBHelper(this)

        val sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)

        binding.recyclerViewContacts.layoutManager = LinearLayoutManager(applicationContext)
        loadList()

        binding.btnLogout.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("username", "")
            editor.apply()
            finish()
        }

        /*
        binding.listViewContacts.setOnItemClickListener { _, _, position, _ ->
            /*Toast.makeText(applicationContext, contactList[position].name, Toast.LENGTH_SHORT)
                .show()*/
            val intent = Intent(applicationContext, ContactDetailMainActivity::class.java)
            intent.putExtra("id", contactList[position].id)
            //startActivity(intent)
            result.launch(intent)
        }
         */

        binding.btnAdd.setOnClickListener {
            result.launch(Intent(applicationContext, NewContactActivity::class.java))
        }

        binding.btnOrder.setOnClickListener {
            if(ascDesc){
                binding.btnOrder.setImageResource(R.drawable.baseline_arrow_upward_24)
            }else{
                binding.btnOrder.setImageResource(R.drawable.baseline_arrow_downward_24)
            }
            ascDesc = !ascDesc
            contactList = contactList.reversed()
            placeAdapter()
        }

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                loadList()
            } else if (it.data != null && it.resultCode == 0) {
                Toast.makeText(applicationContext, "Operation Canceled", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun placeAdapter(){
        adapter = ContactListAdapter(contactList, ContactOnClickListener { contact ->
            val intent = Intent(applicationContext, ContactDetailActivity::class.java)
            intent.putExtra("id", contact.id)
            result.launch(intent)
        })
        binding.recyclerViewContacts.adapter = adapter
    }
    private fun loadList() {
        contactList = dbhelper.getAllContact().sortedWith(compareBy{ it.name})
        placeAdapter()
        /*
        contactList = dbhelper.getAllContact()

        adapter =
            ArrayAdapter(
                applicationContext,
                android.R.layout.simple_list_item_1,
                contactList
            )

        binding.listViewContacts.adapter = adapter

         */
    }

}



