package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        val text:TextView = findViewById(R.id.hello_text)
        val userName = intent.getStringExtra("userName") ?: "guest"
        text.text = "Hello! $userName"

        // מסך אחריכפתור myProfile
        val my_profile_button = findViewById<Button>(R.id.my_profile_button)
        my_profile_button.setOnClickListener {
            val nextPage = Intent(this, MyProfile::class.java)
            nextPage.putExtra("myProfile", my_profile_button.text.toString())
            startActivity(nextPage)
        }
        // Main buttons actions
        findViewById<Button>(R.id.start_workout_button).setOnClickListener {
            Toast.makeText(this, "Start Workout clicked!", Toast.LENGTH_SHORT).show()
        }


        findViewById<Button>(R.id.my_goal_button).setOnClickListener {
            Toast.makeText(this, "My Goal clicked!", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.my_friends_button).setOnClickListener {
            Toast.makeText(this, "My Friends clicked!", Toast.LENGTH_SHORT).show()
        }

    }
}

