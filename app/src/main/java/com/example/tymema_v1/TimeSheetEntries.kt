package com.example.tymema_v1

class TimeSheetEntries(
    var date: String = "",
    var startTime: String = "",
    var endTime: String = "",
    var description: String = "",
    var category: List<String> = listOf()
) {
    companion object {
        val entriesList = mutableListOf<TimeSheetEntries>()
        val categories = mutableListOf<String>()
    }
}

