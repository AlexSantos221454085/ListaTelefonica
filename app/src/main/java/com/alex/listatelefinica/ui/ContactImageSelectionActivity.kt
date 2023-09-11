package com.alex.listatelefinica.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alex.listatelefinica.R
import com.alex.listatelefinica.databinding.ActivityContactImageSelectionBinding

class ContactImageSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactImageSelectionBinding
    private lateinit var i: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactImageSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        i = intent

        binding.imageProfile1.setOnClickListener {  sendID(R.drawable.profile1)}
        binding.imageProfile2.setOnClickListener {  sendID(R.drawable.profile2)}
        binding.imageProfile3.setOnClickListener {  sendID(R.drawable.profile3)}
        binding.imageProfile4.setOnClickListener {  sendID(R.drawable.profile4)}
        binding.imageProfile5.setOnClickListener {  sendID(R.drawable.profile5)}
        binding.imageProfile6.setOnClickListener {  sendID(R.drawable.profile6)}
        binding.btnRemoveImage.setOnClickListener {  sendID(R.drawable.profiledefuault)}


    }

    private fun sendID(id: Int) {
        i.putExtra("id", id)
        setResult(1, i)
        finish()
    }
}