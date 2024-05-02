package com.example.tymema_v1

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import java.text.SimpleDateFormat
import java.util.Locale

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
        val categories = TimeSheetEntries.categories
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        closeButton.setOnClickListener {
            // Handle close button click - navigate back to previous activity
            onBackPressedDispatcher.onBackPressed()
        }

        editTextDate.setOnClickListener {
            openDateDialog(editTextDate)
        }
        editTextStartTime.setOnClickListener {
            openTimeDialog(editTextStartTime)
        }
        editTextEndTime.setOnClickListener {
            openTimeDialog(editTextEndTime)
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
    fun openDateDialog(editTextDate: EditText) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2024)
        calendar.set(Calendar.MONTH, 0) // January is 0-based
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                // Update the editTextDate field
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, monthOfYear, dayOfMonth)

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) // Or your desired format
                editTextDate.setText(dateFormat.format(selectedCalendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
    fun openTimeDialog(editTextTime: EditText) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedCalendar.set(Calendar.MINUTE, minute)

                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault()) // Or your desired format
                editTextTime.setText(timeFormat.format(selectedCalendar.time))
            },
            currentHour,
            currentMinute,
            true // Set 24-hour format (false for AM/PM)
        )
        timePickerDialog.show()
    }

    private fun loadCategories(): List<String> {
        // Use the category list from TimeSheetEntries
        return TimeSheetEntries.categories
    }

}
