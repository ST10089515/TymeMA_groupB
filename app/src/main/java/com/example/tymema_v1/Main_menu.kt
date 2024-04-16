package com.example.tymema_v1

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import android.os.Bundle

class Main_menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
    }
    fun openCreateCategoryActivity(view: View) {
        val intent = Intent(this, CreateCategory::class.java)
        startActivity(intent)
    }

    fun openCreateTimesheetEntryActivity(view: View) {
        val intent = Intent(this, Timesheet::class.java)
        startActivity(intent)
    }
}