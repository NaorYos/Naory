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
        val resetButton: Button = findViewById(R.id.resetButton)

        timerRunnable = object : Runnable {
            override fun run() {
                if (isRunning) {
                    timeElapsed++
                    timerTextView.text = formatTime(timeElapsed)
                    handler.postDelayed(this, 1000)
                }
            }
        }

        runButton.setOnClickListener {
            if (isRunning) {
                isRunning = false
                handler.removeCallbacks(timerRunnable)
                runButton.text = "Run"
            } else {
                isRunning = true
                handler.post(timerRunnable)
                runButton.text = "Stop"
            }
        }

        resetButton.setOnClickListener {
            isRunning = false
            handler.removeCallbacks(timerRunnable)
            timeElapsed = 0
            timerTextView.text = formatTime(timeElapsed)
            runButton.text = "Run"
        }
    }

    private fun formatTime(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(timerRunnable)
    }
}
