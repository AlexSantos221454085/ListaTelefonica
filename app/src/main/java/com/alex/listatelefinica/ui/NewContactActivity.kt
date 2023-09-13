package com.alex.listatelefinica.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.alex.listatelefinica.R
import com.alex.listatelefinica.database.DBHelper
import com.alex.listatelefinica.databinding.ActivityNewContactBinding

class NewContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewContactBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var id: Int? = -1

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
            var imageId = -1
            if (id != null) {
                imageId = id as Int
            }
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
            setResult(0, i)
            finish()
        }

        binding.imageContact.setOnClickListener {
            launcher.launch(Intent(applicationContext, ContactImageSelectionActivity::class.java))

        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                id = it.data?.extras?.getInt("id")
                binding.imageContact.setImageResource(id!!)
            } else {
                id = -1
                binding.imageContact.setImageResource(R.drawable.profiledefuault)
            }
        }
    }
}