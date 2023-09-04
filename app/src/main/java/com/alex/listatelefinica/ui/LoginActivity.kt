package com.alex.listatelefinica.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alex.listatelefinica.R
import com.alex.listatelefinica.database.DBHelper
import com.alex.listatelefinica.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = DBHelper(this)

        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "")
        if (username != null) {
            if (username.isNotEmpty()){
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            val logged = binding.checkboxLogged.isChecked

            if (username.isNotEmpty() && password.isNotEmpty()) {
                if (db.login(username, password)){
                    if (logged){
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("username", username)
                        editor.apply()
                    }
                    startActivity(Intent(this, MainActivity::class.java))
                   //    finish()
                }else{
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.login_error),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.editUsername.setText("")
                    binding.editPassword.setText("")
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.please_insert_all_required_fileds),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        binding.textSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.textRecoverPassowrd.setOnClickListener { }
    }
}