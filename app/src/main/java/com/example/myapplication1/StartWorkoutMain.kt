package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartWorkoutMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_workout_main)

        val runButton = findViewById<Button>(R.id.runButton)
        runButton.setOnClickListener {
            val nextPage = Intent(this, RunWorkout::class.java)
            startActivity(nextPage) }

        val walkButton = findViewById<Button>(R.id.walkButton)
        walkButton.setOnClickListener {
            val nextPage = Intent(this, WalkWorkout::class.java)
            startActivity(nextPage)

    }
}}