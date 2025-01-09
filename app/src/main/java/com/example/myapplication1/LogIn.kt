package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.log_in)
            val btnLogin:Button = findViewById(R.id.btnLogin)
            val signUp:Button = findViewById(R.id.signUp)
            btnLogin.setOnClickListener {
                val Password = findViewById<EditText>(R.id.Password).text.toString()
                val Username = findViewById<EditText>(R.id.Username).text.toString()
                if (Username.isEmpty() || Password.isEmpty()) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val nextPage = Intent(this, MainPage::class.java)
                    nextPage.putExtra("userName",Username)
                    startActivity(nextPage)
                }
            }
            signUp.setOnClickListener {
                val intent = Intent(this, SignUp::class.java)
                startActivity(intent)
            }
        }
    }

