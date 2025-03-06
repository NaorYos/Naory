package com.example.myapplication1

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RunGoal : AppCompatActivity() {
    private var currentUserId: String = ""
    private lateinit var runGoalAdapter: RunAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.run_goal)
        currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        val kmSeekBar = findViewById<SeekBar>(R.id.kmSeekBar)
        val kmTextView = findViewById<TextView>(R.id.kmTextView)
        val timeEditText = findViewById<EditText>(R.id.timeEditText)
        val timeUnitSpinner = findViewById<Spinner>(R.id.timeUnitSpinner)
        val nextButton = findViewById<Button>(R.id.nextButton)
        recyclerView = findViewById(R.id.goalsRecycle)
        recyclerView.layoutManager = LinearLayoutManager(this)
        runGoalAdapter = RunAdapter(emptyList())
        recyclerView.adapter = runGoalAdapter
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
                            fetchRunGoals()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "אירעה שגיאה: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }

        fetchRunGoals()
    }

    private fun fetchRunGoals() {
        val db = FirebaseFirestore.getInstance()
        val runGoalsList = mutableListOf<RunGoalClass>()

        if (currentUserId.isEmpty()) return

        db.collection("users")
            .document(currentUserId)
            .collection("RunGoal")
            .get()
            .addOnSuccessListener { runGoalDocs ->
                for (document in runGoalDocs) {
                    val runGoal = RunGoalClass(
                        km = document.getLong("km")?.toInt() ?: 0,
                        time = document.getString("time") ?: ""
                    )
                    runGoalsList.add(runGoal)
                }
                runGoalAdapter = RunAdapter(runGoalsList)
                recyclerView.adapter = runGoalAdapter
            }
            .addOnFailureListener { e ->
                Log.w("RunGoal", "Error fetching run goals", e)
            }
    }
}