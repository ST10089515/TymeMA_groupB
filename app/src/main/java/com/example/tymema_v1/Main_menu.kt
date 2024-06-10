package com.example.tymema_v1

import android.app.Activity
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
    private lateinit var spinnerHours: Spinner
    private var filteredList: MutableList<TimeSheetEntries> = mutableListOf()
    private lateinit var categoryAdapter: CategoryAdapter
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        recyclerView = findViewById(R.id.recyclerView)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        datePicker = findViewById(R.id.textFilterByDate)
        textReset = findViewById(R.id.textReset)

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val entriesList = TimeSheetEntries.entriesList

        // Initialize adapter with the list of TimeSheetEntries
        adapter = TimeSheetAdapter(this)
        recyclerView.adapter

        categoryAdapter = CategoryAdapter(this)

        // Set the adapter to the RecyclerView
        recyclerView.adapter = adapter

        // Setup ActionBarDrawerToggle to handle opening and closing of the drawer
        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Reference to subButton1 and subButton2
        val subButton1 = findViewById<ImageButton>(R.id.subButton1)
        val subButton2 = findViewById<ImageButton>(R.id.subButton2)
        val textSubButton1 = findViewById<TextView>(R.id.textSubButton1)
        val textSubButton2 = findViewById<TextView>(R.id.textSubButton2)

        // Set click listener to FloatingActionButton (fab)
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
        fetchTimeSheetEntries()

    }
    private fun fetchTimeSheetEntries() {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("timeSheetEntries").child(userId.toString()) // Use userId

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newEntries = snapshot.children.mapNotNull { it.getValue(TimeSheetEntries::class.java) }

                if (newEntries.isEmpty()) {
                    // If no entries found, show the TextView
                    findViewById<TextView>(R.id.textNoEntries).visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE // Hide RecyclerView
                } else {
                    // If entries found, hide the TextView and show the RecyclerView
                    findViewById<TextView>(R.id.textNoEntries).visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    adapter.updateData(newEntries)  // Update TimeSheetAdapter data
                    categoryAdapter.updateData(newEntries) // Update CategoryAdapter data
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Main_menu, "Failed to load entries", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                    GregorianCalendar(selectedYear, selectedMonth, selectedDayOfMonth).time
                )
                datePicker.text = selectedDate
                filterEntriesByDate(selectedDate)
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()
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
            R.id.nav_calendar ->{
                startActivity(Intent(this, CalendarActivity::class.java))
            }

        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun resetActivity() {
        // Refresh the activity to its default state
        val intent = intent
        finish()
        startActivity(intent)
    }
    private fun filterEntriesByDate(selectedDate: String) {
        val filteredEntries = TimeSheetEntries.filterEntriesByDate(selectedDate)

        adapter = TimeSheetAdapter(this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }



    companion object {
        private const val REQUEST_CODE_CREATE_ENTRY = 1001
    }
}
