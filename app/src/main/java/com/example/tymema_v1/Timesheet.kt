package com.example.tymema_v1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Spinner
import android.widget.Button
import android.os.Bundle

class Timesheet : AppCompatActivity() {

    private lateinit var editTextDate: EditText
    private lateinit var editTextStartTime: EditText
    private lateinit var editTextEndTime: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var buttonSaveEntry: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet)
        editTextDate = findViewById(R.id.editTextDate)
        editTextStartTime = findViewById(R.id.editTextStartTime)
        editTextEndTime = findViewById(R.id.editTextEndTime)
        editTextDescription = findViewById(R.id.editTextDescription)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        buttonSaveEntry = findViewById(R.id.buttonSaveEntry)

        buttonSaveEntry.setOnClickListener {
            val date = editTextDate.text.toString()
            val startTime = editTextStartTime.text.toString()
            val endTime = editTextEndTime.text.toString()
            val description = editTextDescription.text.toString()
            val selectedCategory = spinnerCategory.selectedItem.toString()

            // Create an Intent to pass data to another activity
            val intent = Intent(this, DisplayTimesheet::class.java)
            intent.putExtra("date", date)
            intent.putExtra("startTime", startTime)
            intent.putExtra("endTime", endTime)
            intent.putExtra("description", description)
            intent.putExtra("category", selectedCategory)

            // Start the other activity
            startActivity(intent)
        }
    }
}