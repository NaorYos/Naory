package com.example.myapplication1

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.sign_up)

        lateinit var auth: FirebaseAuth
        auth = Firebase.auth
        val currentUser = auth.currentUser

        val enterButton = findViewById<Button>(R.id.buttonEnter)
        enterButton.setOnClickListener {
            val userName = findViewById<EditText>(R.id.UserName).text.toString()
            val password = findViewById<EditText>(R.id.Password).text.toString()
            val nickname = findViewById<EditText>(R.id.Nickname).text.toString() // <-- new

            auth.createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser

                        // ✅ Add user data to Firestore
                        val db = FirebaseFirestore.getInstance()
                        val userMap = hashMapOf(
                            "email" to userName,
                            "uid" to user?.uid,
                            "createdAt" to System.currentTimeMillis()
                        )

                        db.collection("users")
                            .document(user?.uid ?: "")
                            .set(userMap)
                            .addOnSuccessListener {
                                Toast.makeText(this, "User added successfully!", Toast.LENGTH_SHORT).show()
                                val nextPage = Intent(this, MainPage::class.java)
                                nextPage.putExtra("userName", userName)
                                nextPage.putExtra("nickname", nickname)
                                startActivity(nextPage)
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error adding user: ${e.message}", Toast.LENGTH_SHORT).show()
                            }

                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}
