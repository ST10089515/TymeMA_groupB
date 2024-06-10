package com.example.tymema_v1

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PastGoalRecords : AppCompatActivity() {

    private lateinit var listViewRecords: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_past_records)

        listViewRecords = findViewById(R.id.listViewRecords)

        loadRecords()
    }

    private fun loadRecords() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("goals").child(userId)

            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val records = mutableListOf<String>()
                    for (snapshot in dataSnapshot.children) {
                        val date = snapshot.key
                        val minGoal = snapshot.child("minGoal").getValue(Float::class.java) ?: 0f
                        val maxGoal = snapshot.child("maxGoal").getValue(Float::class.java) ?: 0f
                        records.add("Date: $date\nMin Goal: $minGoal Hours\nMax Goal: $maxGoal Hours")
                    }
                    val adapter = ArrayAdapter(this@PastGoalRecords, android.R.layout.simple_list_item_1, records)
                    listViewRecords.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Firebase", "Failed to load records: ${databaseError.message}")
                }
            })
        }
    }
}
