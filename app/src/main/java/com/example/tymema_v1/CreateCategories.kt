package com.example.tymema_v1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Xml
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import org.xmlpull.v1.XmlPullParser
import java.io.StringWriter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId != null) {
                    val database = FirebaseDatabase.getInstance()
                    val reference = database.getReference("categories").child(userId)

                    // Check if the category name already exists
                    reference.orderByValue().equalTo(categoryName).addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Category name already exists
                                editTextCategory.error = "Category name already exists"
                            } else {
                                // Category name doesn't exist, save it
                                val categoryId = reference.push().key
                                if (categoryId != null) {
                                    reference.child(categoryId).setValue(categoryName).addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(this@CreateCategories, "Category saved successfully", Toast.LENGTH_SHORT).show()
                                            Log.d("Firebase", "Category saved: $categoryName")

                                            // Optionally, update the local list of categories
                                            TimeSheetEntries.categories.add(categoryName)

                                            val intent = Intent(this@CreateCategories, Main_menu::class.java)
                                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                                            startActivity(intent)
                                        } else {
                                            Toast.makeText(this@CreateCategories, "Failed to save category", Toast.LENGTH_SHORT).show()
                                            Log.e("Firebase", "Failed to save category", task.exception)
                                        }
                                    }
                                } else {
                                    Log.e("Firebase", "Failed to generate category ID")
                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e("Firebase", "Database error: ${databaseError.message}")
                            // Handle database error
                        }
                    })
                }
            } else {
                editTextCategory.error = "Category name cannot be empty"
            }
        }
    }
}