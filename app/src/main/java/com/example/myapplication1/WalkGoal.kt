package com.example.myapplication1

import WalkAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WalkGoal : AppCompatActivity() {
    private var currentUserId: String = ""
    private lateinit var WalkGoalAdapter: WalkAdapter
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.walk_goal)
        currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        val kmSeekBar = findViewById<SeekBar>(R.id.kmSeekBar)
        val kmTextView = findViewById<TextView>(R.id.kmTextView)
        val timeEditText = findViewById<EditText>(R.id.timeEditText)
        val timeUnitSpinner = findViewById<Spinner>(R.id.timeUnitSpinner)
        val nextButton = findViewById<Button>(R.id.nextButton)
        recyclerView = findViewById(R.id.goalsRecycle)
        recyclerView.layoutManager = LinearLayoutManager(this)
        WalkGoalAdapter = WalkAdapter(emptyList())
        recyclerView.adapter = WalkGoalAdapter
        val firestore = FirebaseFirestore.getInstance()

        kmSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                kmTextView.text = "$progress Km"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        nextButton.setOnClickListener {
            val kmValue = kmSeekBar.progress
            val timeValue = timeEditText.text.toString()
            val selectedUnit = timeUnitSpinner.selectedItem.toString()

            if (timeValue.isEmpty()) {
                Toast.makeText(this, "אנא מלא את הפרטים", Toast.LENGTH_SHORT).show()
            } else {
                val goalData = hashMapOf(
                    "km" to kmValue,
                    "time" to timeValue,
                    "unit" to selectedUnit
                )
                if (currentUserId.isNotEmpty()) {
                    firestore.collection("users")
                        .document(currentUserId)
                        .collection("RunGoal")
                        .document()
                        .set(goalData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "הנתונים נקלטו בהצלחה", Toast.LENGTH_SHORT).show()
                            fetchWalkGoals()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "אירעה שגיאה: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }

        fetchWalkGoals()
    }

    private fun fetchWalkGoals() {
        val db = FirebaseFirestore.getInstance()
        val walkGoalsList = mutableListOf<WalkGoalClass>()

        if (currentUserId.isEmpty()) return

        db.collection("users")
            .document(currentUserId)
            .collection("RunGoal")
            .get()
            .addOnSuccessListener { runGoalDocs ->
                for (document in runGoalDocs) {
                    val walkGoal = WalkGoalClass(
                        km = document.getLong("km")?.toInt() ?: 0,
                        time = document.getString("time") ?: ""
                    )
                    walkGoalsList.add(walkGoal)
                }
                WalkGoalAdapter = WalkAdapter(walkGoalsList)
                recyclerView.adapter = WalkGoalAdapter
            }
            .addOnFailureListener { e ->
                Log.w("WalkGoal", "Error fetching walk goals", e)
            }
    }
}