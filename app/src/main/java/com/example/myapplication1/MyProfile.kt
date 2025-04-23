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

            val text:TextView = findViewById(R.id.usernametext)
            val userName = intent.getStringExtra("myProfile") ?: "guest"
            text.text = "$userName"

            val text2:TextView = findViewById(R.id.Nicknametext)
            val nickname = intent.getStringExtra("nickname") ?: "guest"
            text2.text = "$nickname"

            val text3:TextView = findViewById(R.id.Statustext)
            val Statustext = intent.getStringExtra("Statustext") ?: "guest"
            text3.text = "$Statustext"

        }
    }




