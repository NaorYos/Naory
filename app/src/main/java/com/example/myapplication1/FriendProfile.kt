package com.example.myapplication1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FriendProfile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.friend_profile)

        val user = intent.getSerializableExtra("friendData") as? UserClass

        val nameTextView: TextView = findViewById(R.id.friendName)
        val emailTextView: TextView = findViewById(R.id.friendEmail)

        if (user != null) {
            nameTextView.text = "Name: ${user.Nickname}"
            emailTextView.text = "Email: ${user.email}"
        }
    }
}
