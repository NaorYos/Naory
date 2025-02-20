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

        recyclerView.layoutManager = LinearLayoutManager(this)

        currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        fetchUsers()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterUsers(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterUsers(newText)
                return false
            }
        })
    }

    private fun fetchUsers() {
        val db = FirebaseFirestore.getInstance()

        db.collection("users").get()
            .addOnSuccessListener { result ->
                userList.clear()
                for (document in result) {
                    val user = User(
                        points = document.id,
                        name = document.getString("name") ?: "",
                        email = document.getString("email") ?: ""
                    )
                    if (user.points != currentUserId) {
                        userList.add(user)
                    }
                }
                userAdapter = UserAdapter(userList, currentUserId)
                recyclerView.adapter = userAdapter
            }
            .addOnFailureListener { exception ->
                Log.w("Firestore", "Error getting documents.", exception)
            }
    }

    private fun filterUsers(query: String?) {
        val filteredList = userList.filter {
            it.name.contains(query ?: "", ignoreCase = true)
        }
        userAdapter = UserAdapter(filteredList, currentUserId)
        recyclerView.adapter = userAdapter
    }
}
