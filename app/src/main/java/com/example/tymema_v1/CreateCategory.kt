package com.example.tymema_v1

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Button
import android.os.Bundle

class CreateCategory : AppCompatActivity() {

    private lateinit var editTextCategoryName: EditText
    private lateinit var buttonSaveCategory: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_category)
        editTextCategoryName = findViewById(R.id.editTextCategoryName)
        buttonSaveCategory = findViewById(R.id.buttonSaveCategory)

        buttonSaveCategory.setOnClickListener {
            val categoryName = editTextCategoryName.text.toString()
            // Here you can save the category to the database or perform any other action
            // For simplicity, we'll just print the category name
            println("Category Name: $categoryName")
            finish() // Close the activity after saving
        }
    }

}