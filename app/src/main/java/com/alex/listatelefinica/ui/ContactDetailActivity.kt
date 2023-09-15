package com.alex.listatelefinica.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import android.Manifest
import com.alex.listatelefinica.R
import com.alex.listatelefinica.database.DBHelper
import com.alex.listatelefinica.databinding.ActivityContactDetailMainBinding
import com.alex.listatelefinica.modelo.ContactModel

class ContactDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactDetailMainBinding
    private lateinit var db: DBHelper
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var imageId: Int = -1
    private var contactModel = ContactModel()
    private val REQUEST_PHONE_CALL = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactDetailMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val id = i.extras?.getInt("id")
        db = DBHelper(applicationContext)

        if (id != null) {
            contactModel = db.getContact(id)
            populate()
        } else {
            finish()
        }

        binding.imageEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(contactModel.email))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Send by listaTelefonica APP")

            try {
                startActivity(Intent.createChooser(emailIntent, "Choose Email Client..."))
            } catch (e: Exception) {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.imagePhone.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    REQUEST_PHONE_CALL
                )
            } else {
                val dialIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactModel.phone))
                startActivity(dialIntent)
            }
        }

        binding.btnBack.setOnClickListener {
            setResult(0, i)
            finish()
        }

        binding.btnEdit.setOnClickListener {
            binding.layoutEditDelete.visibility = View.VISIBLE
            binding.layoutEdit.visibility = View.GONE
            changeEditText(true)
        }

        binding.btnCancel.setOnClickListener {
            binding.layoutEditDelete.visibility = View.GONE
            binding.layoutEdit.visibility = View.VISIBLE
            populate()
            changeEditText(false)
        }

        binding.btnSave.setOnClickListener {
            val res = db.updateContact(
                id = contactModel.id,
                name = binding.editName.text.toString(),
                address = binding.editAddress.text.toString(),
                email = binding.editEmail.text.toString(),
                phone = binding.editPhone.text.toString(),
                imageId = imageId
            )

            if (res > 0) {
                Toast.makeText(applicationContext, "Update Ok", Toast.LENGTH_SHORT).show()
                setResult(1, i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Update Error", Toast.LENGTH_SHORT).show()
                setResult(0, i)
                finish()
            }
        }

        binding.btnDelete.setOnClickListener {
            val res = db.deleteContact(contactModel.id)
            if (res > 0) {
                Toast.makeText(applicationContext, "Delete Ok", Toast.LENGTH_SHORT).show()
                setResult(1, i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Delete Error", Toast.LENGTH_SHORT).show()
                setResult(0, i)
                finish()
            }
        }

        binding.imageContact.setOnClickListener {
            if (binding.editName.isEnabled) {
                launcher.launch(
                    Intent(
                        applicationContext,
                        ContactImageSelectionActivity::class.java
                    )
                )
            }
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data != null && it.resultCode == 1) {
                if (it.data?.extras != null) {
                    imageId = it.data?.getIntExtra("id", 0)!!
                    binding.imageContact.setImageResource(imageId!!)
                }
            } else {
                imageId = -1
                binding.imageContact.setImageResource(R.drawable.profiledefuault)
            }
        }
    }

    private fun changeEditText(status: Boolean) {
        binding.editName.isEnabled = status
        binding.editAddress.isEnabled = status
        binding.editEmail.isEnabled = status
        binding.editPhone.isEnabled = status
    }

    private fun populate() {
        binding.editName.setText(contactModel.name)
        binding.editAddress.setText(contactModel.address)
        binding.editEmail.setText(contactModel.email)
        binding.editPhone.setText(contactModel.phone.toString())
        if (contactModel.imageId > 0) {
            binding.imageContact.setImageResource(contactModel.imageId)
        } else {
            binding.imageContact.setImageResource(R.drawable.profiledefuault)
        }
    }
}
