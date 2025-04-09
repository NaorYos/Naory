package com.example.myapplication1

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Login : AppCompatActivity() {

        @SuppressLint("MissingInflatedId")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.login)
            lateinit var auth: FirebaseAuth
            auth = Firebase.auth
            val currentUser = auth.currentUser

            val btnLogin:Button = findViewById(R.id.btnLogin)
            val signUp:Button = findViewById(R.id.signUp)
            btnLogin.setOnClickListener {
                val password = findViewById<EditText>(R.id.password).text.toString()
                val Username = findViewById<EditText>(R.id.Username).text.toString()
                auth.signInWithEmailAndPassword(Username, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser

                            val nextPage = Intent(this, MainPage::class.java)
                            nextPage.putExtra("userName",Username)
                            nextPage.putExtra("password", password)
                            startActivity(nextPage)} else {

                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }


            }
            signUp.setOnClickListener {
                val intent = Intent(this, SignUp::class.java)
                startActivity(intent)
            }
        }
    }

