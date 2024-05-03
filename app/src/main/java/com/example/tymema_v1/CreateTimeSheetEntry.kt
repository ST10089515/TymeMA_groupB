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
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.drjacky.imagepicker.ImagePicker
import java.text.SimpleDateFormat
import java.util.Locale

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
        val categories = TimeSheetEntries.categories
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        closeButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        imageButton.setOnClickListener {
            openImagePicker()
        }

        // Set click listeners for date and time editTexts
        editTextDate.setOnClickListener {
            openDateDialog(editTextDate)
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
                val entry = TimeSheetEntries(date, startTime, endTime, description, listOf(selectedCategory), imagePath)
                TimeSheetEntries.entriesList.add(entry)
                setResult(Activity.RESULT_OK)
                finish()
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

        startActivityForResult(chooserIntent, if (hasGalleryPermission()) GALLERY_REQUEST else CAMERA_REQUEST)
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
