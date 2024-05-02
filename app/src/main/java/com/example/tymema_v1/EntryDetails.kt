package com.example.tymema_v1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EntryDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_details) // Create activity_entry_details.xml

        val entry = intent.getSerializableExtra("entry") as TimeSheetEntries

        // Find your TextViews for displaying the data
        val dateTextView: TextView = findViewById(R.id.entry_date)
        val categoryTextView: TextView = findViewById(R.id.entry_category)
        val descriptionTextView: TextView = findViewById(R.id.entry_description)
        val starttimeTextView: TextView = findViewById(R.id.entry_starttime)
        val endtimeTextView: TextView = findViewById(R.id.entry_endtime)

        // Set the data to the TextViews
        dateTextView.text = entry.date
        categoryTextView.text = entry.category.joinToString(", ")
        descriptionTextView.text = entry.description
        starttimeTextView.text = entry.startTime
        endtimeTextView.text = entry.endTime
    }
}