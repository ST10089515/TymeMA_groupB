package com.example.tymema_v1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class PastGoalRecords : AppCompatActivity() {

    private lateinit var listViewRecords: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_past_records)

        listViewRecords = findViewById(R.id.listViewRecords)

        val records = loadRecords()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, records)
        listViewRecords.adapter = adapter
    }

    private fun loadRecords(): List<String> {
        val sharedPreferences = getSharedPreferences("SessionPrefs", MODE_PRIVATE)
        val records = mutableListOf<String>()
        sharedPreferences.all.keys.forEach { key ->
            if (key.endsWith("-minGoal")) {
                val minGoal = sharedPreferences.getFloat(key, 0f)
                val maxGoalKey = key.replace("-minGoal", "-maxGoal")
                val maxGoal = sharedPreferences.getFloat(maxGoalKey, 0f)
                val date = key.substringBeforeLast("-").substringAfterLast("-")
                records.add("Date: $date\nMin Goal: $minGoal Hours\nMax Goal: $maxGoal Hours")
            }
        }
        return records
    }
<<<<<<< HEAD

=======
>>>>>>> 1104e5e99b754339296b469282ac3a667bcc1a6a
}
