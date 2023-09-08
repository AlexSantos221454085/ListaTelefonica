package com.alex.listatelefinica.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.alex.listatelefinica.R
import com.alex.listatelefinica.database.DBHelper
import com.alex.listatelefinica.databinding.ActivityContactDetailMainBinding
import com.alex.listatelefinica.databinding.ActivityNewContactBinding
import com.alex.listatelefinica.modelo.ContactModel

class ContactDetailMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactDetailMainBinding
    private lateinit var db: DBHelper
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var imageId: Int? = -1
    private var contactModel = ContactModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val id = i.extras?.getInt("id")
        db = DBHelper(applicationContext)

        if (id != null) {
            contactModel = db.getContact(id)

            binding.editName.setText(contactModel.name)
            binding.editAddress.setText(contactModel.address)
            binding.editEmail.setText(contactModel.email)
            binding.editPhone.setText(contactModel.phone.toString())
            binding.imageContact.setImageDrawable(resources.getDrawable(contactModel.imageId))

        }

        binding.btnCancel.setOnClickListener {
            binding.editName.setText(contactModel.name)
            binding.editAddress.setText(contactModel.address)
            binding.editEmail.setText(contactModel.email)
            binding.editPhone.setText(contactModel.phone.toString())
        }
        binding.btnSave.setOnClickListener {
            val res = db.updateContact(
                id = contactModel.id,
                name = binding.editName.text.toString(),
                address = binding.editAddress.text.toString(),
                email = binding.editEmail.text.toString(),
                phone = binding.editPhone.text.toString().toInt(),
                imageId = contactModel.imageId
                )

            if (res > 0){
                Toast.makeText(applicationContext, "Update Ok", Toast.LENGTH_SHORT).show()
                setResult(1,i)
                finish()
            }else{
                Toast.makeText(applicationContext, "Update Error", Toast.LENGTH_SHORT).show()
                setResult(0,i)
                finish()
            }
        }
        binding.btnDelete.setOnClickListener {

            val res = db.deleteContact(contactModel.id)
            if (res > 0){
                Toast.makeText(applicationContext, "Delete Ok", Toast.LENGTH_SHORT).show()
                setResult(1,i)
                finish()
            }else{
                Toast.makeText(applicationContext, "Delete Error", Toast.LENGTH_SHORT).show()
                setResult(0,i)
                finish()
            }
        }
        binding.imageContact.setOnClickListener {
            launcher.launch(Intent(applicationContext, ContactImageSelectionActivity::class.java))

        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                imageId = it.data?.extras?.getInt("id")
                binding.imageContact.setImageDrawable(resources.getDrawable(imageId!!))
            } else {
                imageId = -1
                binding.imageContact.setImageResource(R.drawable.profiledefuault)
            }
        }

    }
}