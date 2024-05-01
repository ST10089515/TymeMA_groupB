package com.example.tymema_v1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class DisplayTimesheet : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_timesheet)

        // Retrieve the data from the intent
        val date = intent.getStringExtra("date")
        val startTime = intent.getStringExtra("startTime")
        val endTime = intent.getStringExtra("endTime")
        val description = intent.getStringExtra("description")
        val category = intent.getStringExtra("category")

        // Display the data in TextViews or wherever appropriate
        val textViewDate: TextView = findViewById(R.id.textViewDate)
        val textViewStartTime: TextView = findViewById(R.id.textViewStartTime)
        val textViewEndTime: TextView = findViewById(R.id.textViewEndTime)
        val textViewDescription: TextView = findViewById(R.id.textViewDescription)
        val textViewCategory: TextView = findViewById(R.id.textViewCategory)

        textViewDate.text = getString(R.string.date_text, date)
        textViewStartTime.text = getString(R.string.start_time_text, startTime)
        textViewEndTime.text = getString(R.string.end_time_text, endTime)
        textViewCategory.text = getString(R.string.category_text, category)
        textViewDescription.text = getString(R.string.description_text, description)
    }
}
