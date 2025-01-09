package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.R

class MyProfile  : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.my_profile)

            val editTextText:TextView = findViewById(R.id.editTextText)
            val userName = intent.getStringExtra("userName") ?: "guest"
            text.text = "Hello! $userName"

        }
    }


