package com.example.tymema_v1

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Main_menu : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TimeSheetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        recyclerView = findViewById(R.id.recyclerView)

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Assuming entriesList is a companion object of TimeSheetEntries
        val entriesList = TimeSheetEntries.entriesList

        // Initialize adapter with the list of TimeSheetEntries
        adapter = TimeSheetAdapter(entriesList)

        // Set the adapter to the RecyclerView
        recyclerView.adapter = adapter

        // Setup the floating action button to launch CreateTimeSheetEntry activity
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            startActivityForResult(Intent(this, CreateTimeSheetEntry::class.java), REQUEST_CODE_CREATE_ENTRY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CREATE_ENTRY && resultCode == Activity.RESULT_OK) {
            // Refresh the activity
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        private const val REQUEST_CODE_CREATE_ENTRY = 1001
    }
}