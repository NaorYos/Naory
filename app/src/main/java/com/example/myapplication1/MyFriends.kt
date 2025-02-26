package com.example.myapplication1

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyFriends : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var userAdapter: UserAdapter
    private val userList = mutableListOf<User>()
    private var currentUserId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_friends)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        
        // עדכון הטקסט ברמז החיפוש
        searchView.queryHint = "Search by Gmail..."
        recyclerView.layoutManager = LinearLayoutManager(this)

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
    }

    private fun searchUsers(query: String) {
        val db = FirebaseFirestore.getInstance()
        
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val filteredUsers = mutableListOf<User>()
                for (document in result) {
                    val user = User(
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
}
