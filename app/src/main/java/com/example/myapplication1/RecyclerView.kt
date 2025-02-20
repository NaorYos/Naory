package com.example.myapplication1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class UserAdapter(private val userList: List<User>, private val currentUserId: String) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.UserName)
        val addFriendButton: Button = itemView.findViewById(R.id.addFriendButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.userName.text = user.name

        holder.addFriendButton.setOnClickListener {
            addFriendToFirestore(currentUserId, user)
        }
    }

    override fun getItemCount(): Int = userList.size

    private fun addFriendToFirestore(currentUserId: String, friend: User) {
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document(currentUserId)
            .collection("friends").document(friend.points)
            .set(mapOf("name" to friend.name, "email" to friend.email))
            .addOnSuccessListener {
                // Successfully added friend
            }
            .addOnFailureListener {
                // Handle error
            }
    }
}
