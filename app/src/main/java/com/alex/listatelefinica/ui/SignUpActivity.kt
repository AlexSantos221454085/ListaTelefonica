package com.alex.listatelefinica.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.alex.listatelefinica.R
import com.alex.listatelefinica.database.DBHelper
import com.alex.listatelefinica.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)

        binding.btnSignup.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            val passwordC = binding.editConfirmPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && passwordC.isNotEmpty()) {

                if (password == passwordC) {
                    val res = db.insertUser(username, password)
                    if (res > 0) {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.signup_ok), Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.signup_error), Toast.LENGTH_SHORT
                        ).show()
                        binding.editUsername.setText("")
                        binding.editPassword.setText("")
                        binding.editConfirmPassword.setText("")
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.passwords_dont_t_match),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.please_insert_all_required_fileds),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}