package com.example.tymema_v1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import java.io.Serializable

class Categories : AppCompatActivity(), RecyclerViewListener, NavigationView.OnNavigationItemSelectedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        recyclerView = findViewById(R.id.rcViewCategories)
        drawerLayout = findViewById(R.id.drawer_layout_category)
        navigationView = findViewById(R.id.navigation_view_category)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val entriesList = TimeSheetEntries.entriesList

        adapter = CategoryAdapter(entriesList, this)
        recyclerView.adapter

        // Set the adapter to the RecyclerView
        recyclerView.adapter = adapter

        // Setup ActionBarDrawerToggle to handle opening and closing of the drawer
        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set navigation item listener
        navigationView.setNavigationItemSelectedListener(this)

        // Set click listener for opening the navigation drawer
        findViewById<ImageButton>(R.id.btnOpenDrawerCategories).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_main_menu -> {
                //Handle Main Menu action
                startActivity(Intent(this, Main_menu::class.java))
            }
            R.id.nav_categories -> {
                // Handle Categories action
                // Not needed as I'm already in the Categories
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onEntryClick(entry: TimeSheetEntries, position: Int) {
        val intent = Intent(this, EntryDetails::class.java)
        intent.putExtra("entry",entry as Serializable)
        startActivity(intent)
    }

}