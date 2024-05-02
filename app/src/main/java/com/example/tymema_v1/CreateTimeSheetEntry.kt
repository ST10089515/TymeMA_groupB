package com.example.tymema_v1

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner

class CreateTimeSheetEntry : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_time_sheet_entry)

        val editTextDate = findViewById<EditText>(R.id.editTextDate)
        val editTextStartTime = findViewById<EditText>(R.id.editTextStartTime)
        val editTextEndTime = findViewById<EditText>(R.id.editTextEndTime)
        val editTextDescription = findViewById<EditText>(R.id.editTextDescription)
        val spinnerCategory = findViewById<Spinner>(R.id.spinnerCategory)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val closeButton = findViewById<ImageView>(R.id.closeButton)

        // Populate categories spinner
        val categories = resources.getStringArray(R.array.categories)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter
        closeButton.setOnClickListener {
            // Handle close button click - navigate back to previous activity
            onBackPressedDispatcher.onBackPressed()
        }

        buttonSave.setOnClickListener {
            // Get input values
            val date = editTextDate.text.toString()
            val startTime = editTextStartTime.text.toString()
            val endTime = editTextEndTime.text.toString()
            val description = editTextDescription.text.toString()
            val selectedCategory = spinnerCategory.selectedItem.toString()

            // Validate input
            if (date.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty() && description.isNotEmpty()) {
                // Create TimeSheetEntry object
                val entry = TimeSheetEntries(date, startTime, endTime, description, listOf(selectedCategory))

                // Add entry to the list
                TimeSheetEntries.entriesList.add(entry)

                // Notify that entry is saved successfully
                setResult(Activity.RESULT_OK)

                // Finish current activity and navigate back to Main_menu activity
                finish()
            }
        }
    }
}
