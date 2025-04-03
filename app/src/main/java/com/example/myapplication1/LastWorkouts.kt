package com.example.myapplication1

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LastWorkouts : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var userAdapter: UserAdapter
    private val userList = mutableListOf<UserClass>()
    private var currentUserId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.last_workouts)

        // התחל עם רשימה ריקה
        userAdapter = UserAdapter(emptyList(), FirebaseAuth.getInstance().currentUser?.uid ?: "")
        recyclerView.adapter = userAdapter
        currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchUsers(query)
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    searchUsers(newText)
                } else {
                    // אם שדה החיפוש ריק, הצג רשימה ריקה
                    userAdapter = UserAdapter(emptyList(), currentUserId)
                    recyclerView.adapter = userAdapter
                }
                return false
            }
        })
        fetchUser()
        friendProfile()
    }

    private fun searchUsers(query: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val filteredUsers = mutableListOf<UserClass>()
                for (document in result) {
                    val user = UserClass(
                        points = document.id,
                        name = document.getString("name") ?: "",
                        email = document.getString("email") ?: ""
                    )
                    // הוסף את המשתמש לרשימה רק אם הוא מתאים לחיפוש ואינו המשתמש הנוכחי
                    if (user.points != currentUserId &&
                        (user.name.contains(query, ignoreCase = true) ||
                                user.email.contains(query, ignoreCase = true))) {
                        filteredUsers.add(user)
                    }
                }
                userAdapter = UserAdapter(filteredUsers, currentUserId)
                recyclerView.adapter = userAdapter
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting documents.", exception)
            }
    }

    //שמירת חברים ברשימה
    private fun fetchUser() {
        val db = FirebaseFirestore.getInstance()
        val friendsList = mutableListOf<UserClass>()

        if (currentUserId.isEmpty()) return

        db.collection("users")
            .document(currentUserId)// Get only the current user's friends
            .collection("userFreinds")
            .get()
            .addOnSuccessListener { userFriendDocs ->
                for (document in userFriendDocs) {
                    val user = UserClass(
                        points = document.id,
                        name = document.getString("name") ?: "",
                        email = document.id ?: "",
                        isFriend = true
                    )
                    friendsList.add(user)
                }
                userAdapter = UserAdapter(friendsList, currentUserId)
                recyclerView.adapter = userAdapter
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error fetching friend details", e)
            }
    }
    private fun friendProfile() {
        val db = FirebaseFirestore.getInstance()
        val friendsList = mutableListOf<UserClass>()

    }
}
