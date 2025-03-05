package com.example.myapplication1

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

class RunGoal : AppCompatActivity() {
    private var currentUserId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.run_goal)
        currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        val kmSeekBar = findViewById<SeekBar>(R.id.kmSeekBar)
        val kmTextView = findViewById<TextView>(R.id.kmTextView)
        val timeEditText = findViewById<EditText>(R.id.timeEditText)
        val timeUnitSpinner = findViewById<Spinner>(R.id.timeUnitSpinner)
        val nextButton = findViewById<Button>(R.id.nextButton)

        val firestore = FirebaseFirestore.getInstance() // חיבור ל-Firestore

        // עדכון טקסט מספר הקילומטרים בזמן אמת
        kmSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                kmTextView.text = "$progress Km"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // לחיצה על כפתור "המשך"
        nextButton.setOnClickListener {
            val kmValue = kmSeekBar.progress // קילומטרים שנבחרו
            val timeValue = timeEditText.text.toString()
            val selectedUnit = timeUnitSpinner.selectedItem.toString()

            if (timeValue.isEmpty()) {
                Toast.makeText(this, "אנא מלא את הפרטים", Toast.LENGTH_SHORT).show()
            } else {
                // יצירת אובייקט Map כדי לשלוח את המידע ל-Firestore
                val goalData = hashMapOf(
                    "km" to kmValue,
                    "time" to timeValue,
                    "unit" to selectedUnit
                )
                val db = FirebaseFirestore.getInstance()
                if (!currentUserId.isEmpty()) {
                    db.collection("users")
                        .document(currentUserId)// Get only the current user's friends
                        .collection("RunGoal")
                        .document()
                        .set(goalData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "הנתונים נקלטו בהצלחה", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "אירעה שגיאה: ${e.message}", Toast.LENGTH_SHORT).show()
                        }

                    // הצגת הודעת הצלחה
                    Toast.makeText(this, "הנתונים נקלטו בהצלחה: ${kmTextView.text}, $timeValue $selectedUnit", Toast.LENGTH_SHORT).show()
                }
                // שמירת המטרה ל-Firestore ב-collection בשם "RunGoal"

            }
        }
    }
}
