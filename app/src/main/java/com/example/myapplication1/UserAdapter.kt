package com.example.myapplication1

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserAdapter(
    private val users: List<UserClass>,
    private val currentUserId: String
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userEmail: TextView = itemView.findViewById(R.id.userEmail)
        val addFriendButton: Button = itemView.findViewById(R.id.addFriendButton)
        val profileButton: Button = itemView.findViewById(R.id.FriendProfile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val auth = FirebaseAuth.getInstance()
        val user = users[position]

        holder.userEmail.text = user.email

        // Add friend logic
        if (user.isFriend) {
            holder.addFriendButton.visibility = View.GONE
        }

        holder.addFriendButton.setOnClickListener {
            val user1 = auth.currentUser
            val db = FirebaseFirestore.getInstance()
            val userMap = hashMapOf(
                "user name" to user.Nickname,
                "Added At" to System.currentTimeMillis()
            )

            db.collection("users")
                .document(user1?.uid ?: "")
                .collection("userFreinds")
                .document(user.email)
                .set(userMap)
                .addOnSuccessListener {
                    Log.d(TAG, "Added Friend")
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "$e")
                }
        }

        // Profile button logic
        holder.profileButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, FriendProfile::class.java)
            intent.putExtra("friendData", user)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = users.size
}
