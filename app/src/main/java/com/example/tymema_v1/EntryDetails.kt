package com.example.tymema_v1

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
class EntryDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_details) // Create activity_entry_details.xml

        val dateTextView: TextView = findViewById(R.id.entry_date)
        val categoryTextView: TextView = findViewById(R.id.entry_category)
        val descriptionTextView: TextView = findViewById(R.id.entry_description)
        val starttimeTextView: TextView = findViewById(R.id.entry_starttime)
        val endtimeTextView: TextView = findViewById(R.id.entry_endtime)
        val entryImageView: ImageView = findViewById(R.id.entry_image)

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        val entry = intent.getSerializableExtra("entry") as? TimeSheetEntries

        if (userId != null && entry != null) {
            // Use the entry object directly
            dateTextView.text = entry.date
            categoryTextView.text = entry.category.joinToString(", ")
            descriptionTextView.text = entry.description
            starttimeTextView.text = entry.startTime
            endtimeTextView.text = entry.endTime
            entry.imagePath?.let {
                entryImageView.setImageURI(Uri.parse(it))
            }
        }
    }
}