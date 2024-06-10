package com.example.tymema_v1

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import java.text.SimpleDateFormat
import java.util.Locale
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CreateTimeSheetEntry : AppCompatActivity() {

    private var imagePath: String? = null
    private lateinit var imageButton: ImageView

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 101
        private const val GALLERY_PERMISSION_REQUEST_CODE = 102
        private const val CAMERA_REQUEST = 1888
        private const val GALLERY_REQUEST = 1889
    }

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
        imageButton = findViewById<ImageView>(R.id.imageButton)

        // Populate categories spinner
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val database = FirebaseDatabase.getInstance()
            val reference = database.getReference("categories").child(userId)
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val categoriesList = mutableListOf<String>()
                    for (categorySnapshot in dataSnapshot.children) {
                        val categoryName = categorySnapshot.getValue(String::class.java)
                        if (categoryName != null) {
                            categoriesList.add(categoryName)
                        }
                    }
                    val adapter = ArrayAdapter(
                        this@CreateTimeSheetEntry,
                        android.R.layout.simple_spinner_item,
                        categoriesList
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerCategory.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Firebase", "Failed to retrieve categories: ${databaseError.message}")
                    // Handle the error here
                }
            })
        }

        closeButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        imageButton.setOnClickListener {
            openImagePicker()
        }

        // Set click listeners for date and time editTexts
        // If prefilledDate is passed from CalendarActivity, set it in editTextDate
        val prefilledDate = intent.getStringExtra("prefilledDate")
        if (!prefilledDate.isNullOrEmpty()) {
            editTextDate.setText(prefilledDate)
        }
        else{
            editTextDate.setOnClickListener {
                openDateDialog(editTextDate)
            }
        }
        editTextStartTime.setOnClickListener {
            openTimeDialog(editTextStartTime)
        }
        editTextEndTime.setOnClickListener {
            openTimeDialog(editTextEndTime)
        }

        if (!hasCameraPermission()) {
            requestCameraPermission()
        }
        if (!hasGalleryPermission()) {
            requestGalleryPermission()
        }

        buttonSave.setOnClickListener {
            val date = editTextDate.text.toString()
            val startTime = editTextStartTime.text.toString()
            val endTime = editTextEndTime.text.toString()
            val description = editTextDescription.text.toString()
            val selectedCategory = spinnerCategory.selectedItem.toString()

            if (date.isNotEmpty() && startTime.isNotEmpty() && endTime.isNotEmpty() && description.isNotEmpty()) {
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                Log.d("Firebase","userid: $userId") //just a log to see if it's retrieving the userid, if it's null then firebase auth wasn't properly setup
                if (userId != null) {
                    val database = FirebaseDatabase.getInstance()
                    val reference = database.getReference("timeSheetEntries").child(userId)
                    val entryId = reference.push().key
                    Log.d("Firebase","entryid: $entryId")
                    if (entryId != null) {
                        val entry = TimeSheetEntries(entryId, userId, date, startTime, endTime, description, listOf(selectedCategory), imagePath)
                        reference.child(entryId).setValue(entry).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Entry saved successfully", Toast.LENGTH_SHORT).show()
                                Log.d("Firebase", "Entry saved: $entry")
                                setResult(Activity.RESULT_OK)
                                finish()
                            } else {
                                Toast.makeText(this, "Failed to save entry", Toast.LENGTH_SHORT).show()
                                Log.e("Firebase", "Failed to save entry", task.exception)
                            }
                        }
                    } else {
                        Log.e("Firebase", "Failed to generate entry ID")
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun hasGalleryPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }
    private fun requestGalleryPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            GALLERY_PERMISSION_REQUEST_CODE
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE || requestCode == GALLERY_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, do nothing
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST -> {
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    imageButton.setImageBitmap(imageBitmap)
                }
                GALLERY_REQUEST -> {
                    val selectedImageUri = data?.data
                    imageButton.setImageURI(selectedImageUri)
                }
            }
        }
    }
    private fun openImagePicker() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        val chooserIntent = Intent.createChooser(galleryIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))

        if (hasCameraPermission() && hasGalleryPermission()) {
            startActivityForResult(chooserIntent, GALLERY_REQUEST)
        } else {
            if (!hasCameraPermission()) {
                requestCameraPermission()
            }
            if (!hasGalleryPermission()) {
                requestGalleryPermission()
            }
        }
    }


    private fun openDateDialog(editTextDate: EditText) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2024)
        calendar.set(Calendar.MONTH, 0) // January is 0-based
        calendar.set(Calendar.DAY_OF_MONTH, 1)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, monthOfYear, dayOfMonth)

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                editTextDate.setText(dateFormat.format(selectedCalendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun openTimeDialog(editTextTime: EditText) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedCalendar.set(Calendar.MINUTE, minute)

                val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                editTextTime.setText(timeFormat.format(selectedCalendar.time))
            },
            currentHour,
            currentMinute,
            true // 24-hour format
        )
        timePickerDialog.show()
    }
}
