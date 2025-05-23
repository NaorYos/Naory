package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class HomePage : AppCompatActivity() {
     override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home_page)
         val signUp:Button = findViewById(R.id.signupButton)
         val logIn:Button = findViewById(R.id.loginButton)
         signUp.setOnClickListener{
             val nextPage = Intent(this, SignUp::class.java)
             startActivity(nextPage)
         }

         logIn.setOnClickListener{val nextPage = Intent(this, Login::class.java)
             startActivity(nextPage)}
     }
}