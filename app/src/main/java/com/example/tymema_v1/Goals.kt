package com.example.tymema_v1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*

class Goals : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //private lateinit var sharedPreferences: SharedPreferences
    private lateinit var btnSave: Button
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_tracker)

        //sharedPreferences = getSharedPreferences("SessionPrefs", Context.MODE_PRIVATE)

        btnSave = findViewById(R.id.btnSave)
        val btnViewRecords = findViewById<Button>(R.id.btnViewRecords)

        btnSave.setOnClickListener {
            saveRecord()
        }

        btnViewRecords.setOnClickListener {
            startActivity(Intent(this, PastGoalRecords::class.java))
        }

        loadSessionTime()
    }

    private fun saveRecord() {
        val edtMinGoal = findViewById<EditText>(R.id.edtMinGoal)
        val edtMaxGoal = findViewById<EditText>(R.id.edtMaxGoal)

        if (edtMinGoal == null || edtMaxGoal == null) {
            Toast.makeText(this, "Error: Views not found", Toast.LENGTH_SHORT).show()
            return
        }

        val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val minGoal = edtMinGoal.text.toString().toFloat()
        val maxGoal = edtMaxGoal.text.toString().toFloat()

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("goals").child(userId).child(currentDate)

            val goalData = mapOf(
                "minGoal" to minGoal,
                "maxGoal" to maxGoal
            )

            reference.setValue(goalData).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Record saved for $currentDate", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to save record", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadSessionTime() {
        val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("goals").child(userId).child(currentDate)

            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val minGoal = dataSnapshot.child("minGoal").getValue(Float::class.java) ?: 0f
                    val maxGoal = dataSnapshot.child("maxGoal").getValue(Float::class.java) ?: 0f

                    val edtMinGoal = findViewById<EditText>(R.id.edtMinGoal)
                    val edtMaxGoal = findViewById<EditText>(R.id.edtMaxGoal)

                    edtMinGoal.setText(minGoal.toString())
                    edtMaxGoal.setText(maxGoal.toString())
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Firebase", "Failed to load session time: ${databaseError.message}")
                }
            })
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_main_menu -> {
                // Handle Main Menu action
                startActivity(Intent(this,Main_menu::class.java))
            }
            R.id.nav_categories -> {
                // Handle Categories action
                startActivity(Intent(this, Categories::class.java))
            }
            R.id.nav_goals ->{
                // No action needed as you are already in Goals
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
