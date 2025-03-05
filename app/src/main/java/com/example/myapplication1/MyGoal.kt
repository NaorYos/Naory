package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication1.R

class MyGoal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_goal)

        val userName = intent.getStringExtra("userName") ?: "guest"
        val runButton = findViewById<Button>(R.id.runButton)
        runButton.setOnClickListener {
            val nextPage = Intent(this, RunGoal::class.java)
            nextPage.putExtra("runButton", userName)
            startActivity(nextPage)
        }
    }
}