package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.sign_up)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Initialize UI elements with correct IDs
        val enterButton = findViewById<Button>(R.id.buttonEnter)
        enterButton.setOnClickListener {
            val userName = findViewById<EditText>(R.id.UserName).text.toString()
            Toast.makeText(this, userName, Toast.LENGTH_SHORT).show()
            val nextPage = Intent(this, MainPage::class.java)
            nextPage.putExtra("userName", userName)
            startActivity(nextPage)
        }

        // Set up Enter button click listener

    }
}
