package com.example.tymema_v1

import android.content.Intent
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.CalendarView
import android.widget.ImageButton
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class CalendarActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        drawerLayout = findViewById(R.id.drawer_layout_calendar)
        navigationView = findViewById(R.id.navigation_view_calendar)

        // Setup ActionBarDrawerToggle to handle opening and closing of the drawer
        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set navigation item listener
        navigationView.setNavigationItemSelectedListener(this)

        val calendarView = findViewById<CalendarView>(R.id.calendarView)

        // Handle user interaction with calendar dates
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year" // Adjust month index
            openCreateTimeSheetEntry(selectedDate)
        }

        // Set click listener for opening the navigation drawer
        findViewById<ImageButton>(R.id.btnOpenDrawerCalendar).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun openCreateTimeSheetEntry(selectedDate: String) {
        val intent = Intent(this, CreateTimeSheetEntry::class.java)
        intent.putExtra("prefilledDate", selectedDate)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_main_menu -> {
                // Handle Main Menu action
                startActivity(Intent(this, Main_menu::class.java))
            }
            R.id.nav_categories -> {
                // Handle Categories action
                startActivity(Intent(this, Categories::class.java))
            }
            R.id.nav_goals ->{
                startActivity(Intent(this, Goals::class.java))
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}

