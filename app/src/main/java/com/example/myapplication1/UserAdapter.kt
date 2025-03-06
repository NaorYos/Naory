package com.example.myapplication1

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class UserAdapter(
    private val users: List<UserClass>,
    private val currentUserId: String
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userEmail: TextView = itemView.findViewById(R.id.userEmail)
        val addFriendButton: Button = itemView.findViewById(R.id.addFriendButton)
    }
//ניפוח קובייה אל תוך parent(MyFriends)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }
//הוספת מידע לקובייה
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        lateinit var auth: FirebaseAuth
        auth = Firebase.auth
        val user = users[position]
        holder.userEmail.text = user.email
        if (user.isFriend)
        {
            holder.addFriendButton.visibility = View.INVISIBLE
        }
        holder.addFriendButton.setOnClickListener {
            // Add friend logic here
            val user1 = auth.currentUser

            // Create Firestore instance
            val db = FirebaseFirestore.getInstance()

            // Create user data map
            val userMap = hashMapOf(
                "user name" to user.name,
                "Added At" to System.currentTimeMillis()
            )

            // Add user to database
            db.collection("users")
                .document(user1?.uid ?: "")
                .collection("userFreinds")
                .document(user?.email ?: "")
                .set(userMap)
                .addOnSuccessListener {
                    Log.d(TAG,"Added Freind")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG,"${e}")

                }
        }
    }

    override fun getItemCount() = users.size
} 