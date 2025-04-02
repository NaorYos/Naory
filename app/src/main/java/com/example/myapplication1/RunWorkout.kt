package com.example.myapplication1

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class RunWorkout : AppCompatActivity() {

    private var timeElapsed = 0
    private var isRunning = false
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var timerRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.run_workout)

        val timerTextView: TextView = findViewById(R.id.timerTextView)
        val runButton: Button = findViewById(R.id.runButton)
        val resetButton: Button = findViewById(R.id.resetButton) // Added Reset button

        timerRunnable = object : Runnable {
            override fun run() {
                if (isRunning) {
                    timeElapsed++
                    timerTextView.text = "Time: $timeElapsed sec"
                    handler.postDelayed(this, 1000)
                }
            }
        }

        runButton.setOnClickListener {
            if (isRunning) {
                // Stop the timer
                isRunning = false
                handler.removeCallbacks(timerRunnable)
                runButton.text = "Run"
            } else {
                // Start the timer
                isRunning = true
                handler.post(timerRunnable)
                runButton.text = "Stop"
            }
        }

        resetButton.setOnClickListener { // Added Reset button functionality
            isRunning = false // Stop the timer
            handler.removeCallbacks(timerRunnable) // Ensure timer stops
            timeElapsed = 0 // Reset time
            timerTextView.text = "Time: 0 sec" // Update UI
            runButton.text = "Run" // Reset button text
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(timerRunnable) // Ensure timer stops when activity is destroyed
    }
}
