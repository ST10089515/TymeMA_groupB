package com.example.tymema_v1

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import android.os.Bundle
<<<<<<< Updated upstream
=======
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.Spinner
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.Locale

class Main_menu : AppCompatActivity(), RecyclerViewListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TimeSheetAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var datePicker: TextView
    private lateinit var textReset: TextView
>>>>>>> Stashed changes

class Main_menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
<<<<<<< Updated upstream
=======

        recyclerView = findViewById(R.id.recyclerView)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        datePicker = findViewById(R.id.textFilterByDate)
        textReset = findViewById(R.id.textReset)
        val subButton1 = findViewById<ImageButton>(R.id.subButton1)
        val subButton2 = findViewById<ImageButton>(R.id.subButton2)
        val textSubButton1 = findViewById<TextView>(R.id.textSubButton1)
        val textSubButton2 = findViewById<TextView>(R.id.textSubButton2)

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


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            // Toggle visibility of subButton1 and subButton2
            if (subButton1.visibility == View.VISIBLE) {
                subButton1.visibility = View.INVISIBLE
                textSubButton1.visibility = View.INVISIBLE
                subButton2.visibility = View.INVISIBLE
                textSubButton2.visibility = View.INVISIBLE
            } else {
                subButton1.visibility = View.VISIBLE
                textSubButton1.visibility = View.VISIBLE
                subButton2.visibility = View.VISIBLE
                textSubButton2.visibility = View.VISIBLE
            }
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

        datePicker.setOnClickListener {
            showDatePicker()
        }

        textReset.setOnClickListener {
            resetActivity()
        }

>>>>>>> Stashed changes
    }
    fun openCreateCategoryActivity( view: View) {
        val intent = Intent(this, CreateCategory::class.java)
        startActivity(intent)
    }

    fun openCreateTimesheetEntryActivity(view: View) {
        val intent = Intent(this, Timesheet::class.java)
        startActivity(intent)
    }

    fun openDisplayTimesheet(view: View) {
        val intent = Intent(this, DisplayTimesheet::class.java)
        startActivity(intent)
    }
}