package com.example.myapplication1

import android.content.Context // --------------------------
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.EditText

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
        val saveButton: Button = findViewById(R.id.saveButton) // --------------------------
        val summaryTextView: TextView = findViewById(R.id.summaryTextView) // --------------------------
        val kmEditText: EditText = findViewById(R.id.kmEditText)

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
            kmEditText.setText("")
            summaryTextView.text = "" // --------------------------
        }

        saveButton.setOnClickListener {
            val kilometers = kmEditText.text.toString().toFloatOrNull() ?: 0f // --------------------------
            val time = formatTime(timeElapsed) // --------------------------

            // -------------------------- Save to SharedPreferences
            val sharedPref = getSharedPreferences("WorkoutPrefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putFloat("last_km", kilometers)
                putString("last_time", time)
                apply()
            }

            // -------------------------- Show in summary
            summaryTextView.text = "Saved:\nTime: $time\nDistance: ${"%.2f".format(kilometers)} km"
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
