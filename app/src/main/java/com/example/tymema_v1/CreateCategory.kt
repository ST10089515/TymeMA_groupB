package com.example.tymema_v1

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Button
import android.os.Bundle
import android.widget.ImageView

class CreateCategory : AppCompatActivity() {

    private lateinit var editTextCategoryName: EditText
    private lateinit var buttonSaveCategory: Button
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_category)
        editTextCategoryName = findViewById(R.id.editTextCategoryName)
        buttonSaveCategory = findViewById(R.id.buttonSaveCategory)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        buttonSaveCategory.setOnClickListener {
            val categoryName = editTextCategoryName.text.toString()
            // Here you can save the category to the database or perform any other action
            // For simplicity, we'll just print the category name

            // Save the category name to SharePreferences
            saveCategoryName(categoryName)

            finish() // Close the activity after saving
        }

        // Set onClickListener for the back button
        val backButton: ImageView = findViewById(R.id.backButton)
        backButton.setOnClickListener{
            finish() // This will navigate back to the previous screen
        }
    }

    private fun saveCategoryName(categoryName: String){
        val editor = sharedPreferences.edit()
        editor.putString("categoryName", categoryName)
        editor.apply()
    }

}