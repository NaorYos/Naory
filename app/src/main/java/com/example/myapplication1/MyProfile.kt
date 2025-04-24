package com.example.myapplication1

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyProfile : AppCompatActivity() {

    private val PREFS_NAME = "UserProfilePrefs"
    private val KEY_AVATAR = "selected_avatar"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_profile)

        val auth = FirebaseAuth.getInstance()
        val user1 = auth.currentUser
        val db = FirebaseFirestore.getInstance()

        val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        var selectedAvatarRes = sharedPrefs.getInt(KEY_AVATAR, R.drawable.avatar6)

        val avatarImageView: ImageView = findViewById(R.id.avatar6)
        avatarImageView.setImageResource(selectedAvatarRes)

        val avatarIds = listOf(
            R.id.avatar1 to R.drawable.avatar1,
            R.id.avatar2 to R.drawable.avatar2,
            R.id.avatar3 to R.drawable.avatar3,
            R.id.avatar4 to R.drawable.avatar4,
            R.id.avatar5 to R.drawable.avatar5,
            R.id.avatar6 to R.drawable.avatar6
        )

        // Handle avatar selection and store locally
        for ((viewId, resId) in avatarIds) {
            findViewById<ImageView>(viewId).setOnClickListener {
                avatarImageView.setImageResource(resId)
                selectedAvatarRes = resId

                sharedPrefs.edit().putInt(KEY_AVATAR, selectedAvatarRes).apply()
                Toast.makeText(this, "Avatar saved locally", Toast.LENGTH_SHORT).show()
            }
        }

        val text: TextView = findViewById(R.id.usernametext)
        val userName = intent.getStringExtra("myProfile") ?: "guest"
        text.text = userName

        val text2: TextView = findViewById(R.id.Nicknametext)
        val nickname = intent.getStringExtra("nickname") ?: "guest"
        text2.text = nickname

        val text3: TextView = findViewById(R.id.Statustext)
        val saveButton: Button = findViewById(R.id.save_button)

        // Save profile to Firestore
        saveButton.setOnClickListener {
            val userMap = hashMapOf(
                "uid" to user1?.uid.toString(),
                "status" to text3.text.toString(),
                "email" to text.text.toString()
                // avatar not saved to Firestore
            )

            db.collection("users")
                .document(user1?.uid ?: "")
                .set(userMap)
                .addOnSuccessListener {
                    Log.d(TAG, "Status saved")
                    Toast.makeText(this, "Profile saved!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "$e")
                    Toast.makeText(this, "Failed to save profile", Toast.LENGTH_SHORT).show()
                }
        }

        // Load status from Firestore
        db.collection("users")
            .document(user1?.uid ?: "")
            .get()
            .addOnSuccessListener { res ->
                text3.text = res.getString("status")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error fetching user data", e)
            }
    }
}
