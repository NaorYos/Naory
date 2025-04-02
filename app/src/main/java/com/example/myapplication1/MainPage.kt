package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        val text: TextView = findViewById(R.id.hello_text)
        val userName = intent.getStringExtra("userName") ?: "guest"
        text.text = "Hello $userName"

        // מסך אחריכפתור myProfile
            val my_profile_button = findViewById<Button>(R.id.my_profile_button)
        my_profile_button.setOnClickListener {
            val nextPage = Intent(this, MyProfile::class.java)
            nextPage.putExtra("myProfile", userName)
            startActivity(nextPage)
        }
        // Main buttons actions
        val start_workout_button = findViewById<Button>(R.id.start_workout_button)
       start_workout_button.setOnClickListener {
                val nextPage = Intent(this, StartWorkoutMain::class.java)
                nextPage.putExtra("start_workout_button", userName)
                startActivity(nextPage)
        }

        val myGoal_button =  findViewById<Button>(R.id.my_goal_button)
        myGoal_button.setOnClickListener {
                val nextPage = Intent(this, MyGoal::class.java)
                nextPage.putExtra("myGoal", userName)
                startActivity(nextPage)
        }
        val my_freinds_button = findViewById<Button>(R.id.my_friends_button)
        my_freinds_button.setOnClickListener {
                val nextPage = Intent(this, MyFriends::class.java)
                nextPage.putExtra("myFriends", userName)
                startActivity(nextPage)
            }

        }
    }

