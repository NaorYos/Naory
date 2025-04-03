package com.example.myapplication1

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class WalkWorkout : AppCompatActivity() {

    private var timeElapsed = 0
    private var isWalking = false
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var timerWalkable: Runnable

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.walk_workout)

        val timerTextView: TextView = findViewById(R.id.timerTextView)
        val walkButton: Button = findViewById(R.id.walkButton)
        val resetButton: Button = findViewById(R.id.resetButton) // Added Reset button

        timerWalkable = object : Runnable {
            override fun run() {
                if (isWalking) {
                    timeElapsed++
                    timerTextView.text = formatTime(timeElapsed) // Changed to use formatted time
                    handler.postDelayed(this, 1000)
                }
            }
        }

        walkButton.setOnClickListener {
            if (isWalking) {
                // Stop the timer
                isWalking = false
                handler.removeCallbacks(timerWalkable)
                walkButton.text = "Run"
            } else {
                // Start the timer
                isWalking = true
                handler.post(timerWalkable)
                walkButton.text = "Stop"
            }
        }

        resetButton.setOnClickListener { // Added Reset button functionality
            isWalking = false // Stop the timer
            handler.removeCallbacks(timerWalkable) // Ensure timer stops
            timeElapsed = 0 // Reset time
            timerTextView.text = formatTime(timeElapsed) // Changed to use formatted time
            walkButton.text = "Run" // Reset button text
        }
    }
    private fun formatTime(seconds: Int): String { // Added function to format time correctly
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(timerWalkable) // Ensure timer stops when activity is destroyed
    }
}
