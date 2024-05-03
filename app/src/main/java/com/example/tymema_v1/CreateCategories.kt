package com.example.tymema_v1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Xml
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import org.xmlpull.v1.XmlPullParser
import java.io.StringWriter

class CreateCategories : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_categories)

        val editTextCategory: EditText = findViewById(R.id.editTextCategory)
        val buttonSave: Button = findViewById(R.id.buttonSave)
        val closeButton: ImageView = findViewById(R.id.closeButton)

        closeButton.setOnClickListener {
            // Navigate back to the activity_main_menu activity
            finish()
        }

        buttonSave.setOnClickListener {
            val categoryName = editTextCategory.text.toString().trim()

            if (categoryName.isNotEmpty()) {
                // Save the entered category into the categories list
                TimeSheetEntries.categories.add(categoryName)

                // Navigate back to the activity_main_menu activity
                val intent = Intent(this, Main_menu::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            } else {
                // Show error message for empty category
                editTextCategory.error = "Category name cannot be empty"
            }
        }
    }
}