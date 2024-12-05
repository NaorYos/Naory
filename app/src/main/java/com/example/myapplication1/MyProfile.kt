package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.R

class MyProfile  : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.my_profile)

            // Find the TextView in your MyProfile layout to display the received data
            val profileTextView: TextView = findViewById(R.id.profile_text)

            // Retrieve the intent and get the data sent
            val intent = intent
            val myProfileText = intent.getStringExtra("myProfile") ?: "No Profile Info"

            // Display the received data in the TextView
            profileTextView.text = myProfileText

            // Optional: Show a Toast message with the received data
            Toast.makeText(this, "Received: $myProfileText", Toast.LENGTH_SHORT).show()
        }
    }


