package com.example.tymema_v1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
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
import java.text.SimpleDateFormat
import java.util.*

class Goals : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var btnSave: Button
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_tracker)

        sharedPreferences = getSharedPreferences("SessionPrefs", Context.MODE_PRIVATE)

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

        // Check if any of the views are null
        if (edtMinGoal == null || edtMaxGoal == null) {
            Toast.makeText(this, "Error: Views not found", Toast.LENGTH_SHORT).show()
            return
        }

        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val minGoal = edtMinGoal.text.toString().toFloat()
        val maxGoal = edtMaxGoal.text.toString().toFloat()

        val editor = sharedPreferences.edit()
        editor.putFloat("$currentDate-minGoal", minGoal)
        editor.putFloat("$currentDate-maxGoal", maxGoal)
        editor.apply()

        Toast.makeText(this, "Record saved for $currentDate", Toast.LENGTH_SHORT).show()
    }

    private fun loadSessionTime() {
        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val minGoal = sharedPreferences.getFloat("$currentDate-minGoal", 0f)
        val maxGoal = sharedPreferences.getFloat("$currentDate-maxGoal", 0f)

        val edtMinGoal = findViewById<EditText>(R.id.edtMinGoal)
        val edtMaxGoal = findViewById<EditText>(R.id.edtMaxGoal)

        edtMinGoal.setText(minGoal.toString())
        edtMaxGoal.setText(maxGoal.toString())
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
