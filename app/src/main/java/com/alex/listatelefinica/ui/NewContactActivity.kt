package com.alex.listatelefinica.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alex.listatelefinica.R
import com.alex.listatelefinica.database.DBHelper
import com.alex.listatelefinica.databinding.ActivityNewContactBinding

class NewContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(applicationContext)
        val i = intent

        binding.btnSave.setOnClickListener {
            val name = binding.editName.text.toString()
            val address = binding.editAddress.text.toString()
            val email = binding.editEmail.text.toString()
            val phone = binding.editPhone.text.toString().toInt()
            val imageId = 1

            if (name.isNotEmpty() && address.isNotEmpty() && email.isNotEmpty()) {
                val res = db.insertContact(name, address, email, phone, imageId)
                if (res > 0) {
                    Toast.makeText(applicationContext, "Insert Ok", Toast.LENGTH_SHORT).show()
                    setResult(1, i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Insert Error", Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding.btnCancel.setOnClickListener {
            setResult(0,i)
            finish()
        }
    }
}