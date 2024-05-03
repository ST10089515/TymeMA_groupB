package com.example.tymema_v1

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.io.Serializable

class Main_menu : AppCompatActivity(), RecyclerViewListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TimeSheetAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        recyclerView = findViewById(R.id.recyclerView)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val entriesList = TimeSheetEntries.entriesList

        // Initialize adapter with the list of TimeSheetEntries
        adapter = TimeSheetAdapter(entriesList, this)
        recyclerView.adapter

        // Set the adapter to the RecyclerView
        recyclerView.adapter = adapter

        // Setup ActionBarDrawerToggle to handle opening and closing of the drawer
        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Setup the floating action button to launch CreateTimeSheetEntry activity
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            startActivityForResult(Intent(this, CreateTimeSheetEntry::class.java), REQUEST_CODE_CREATE_ENTRY)
        }

        // Setup subButton1 to launch CreateCategories activity
        findViewById<ImageButton>(R.id.subButton1).setOnClickListener {
            startActivity(Intent(this, CreateCategories::class.java))
        }

        // Setup subButton2 to launch CreateTimeSheetEntry activity
        findViewById<ImageButton>(R.id.subButton2).setOnClickListener {
            startActivityForResult(Intent(this, CreateTimeSheetEntry::class.java), REQUEST_CODE_CREATE_ENTRY)
        }

        // Set navigation item listener
        navigationView.setNavigationItemSelectedListener(this)

        // Set click listener for opening the navigation drawer
        findViewById<ImageButton>(R.id.btnOpenDrawer).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CREATE_ENTRY && resultCode == Activity.RESULT_OK) {
            // Refresh the activity
            adapter.notifyDataSetChanged()
        }
    }

    override fun onEntryClick(entry: TimeSheetEntries, position: Int) {
        val intent = Intent(this, EntryDetails::class.java)
        intent.putExtra("entry",entry as Serializable)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_main_menu -> {
                // Handle Main Menu action
                // No action needed as you are already in Main Menu
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

    companion object {
        private const val REQUEST_CODE_CREATE_ENTRY = 1001
    }
}
