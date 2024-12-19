package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class LogIn {
    class Login : AppCompatActivity() {

        private lateinit var etUsername: EditText
        private lateinit var etPassword: EditText
        private lateinit var tvForgotPassword: TextView
        private lateinit var tvSignUp: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.log_in)
            val btnLogin:Button = findViewById(R.id.btnLogin)

            // Initialize UI components
            etUsername = findViewById(R.id.etUsername)
            etPassword = findViewById(R.id.etPassword)
            tvForgotPassword = findViewById(R.id.tvForgotPassword)
            tvSignUp = findViewById(R.id.tvSignUp)

            // Set onClickListener for Login Button
            btnLogin.setOnClickListener {

                val username = etUsername.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                } else {
                    // TODO: Add authentication logic here
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val nextPage = Intent(this, HomePage::class.java)
                    intent.putExtra("btnLogin",etUsername.text.toString())
                    startActivity(nextPage)
                }
            }

            // Set onClickListener for Forgot Password
            tvForgotPassword.setOnClickListener {
                // TODO: Navigate to Forgot Password screen
                Toast.makeText(this, "Forgot Password Clicked", Toast.LENGTH_SHORT).show()
            }

            // Set onClickListener for Sign Up
            tvSignUp.setOnClickListener {
                // Navigate to Sign Up activity
                val intent = Intent(this, SignUp::class.java)
                startActivity(intent)
            }
        }
    }}

