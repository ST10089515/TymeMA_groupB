package com.example.tymema_v1

import java.io.Serializable

class TimeSheetEntries(
    var date: String = "",
    var startTime: String = "",
    var endTime: String = "",
    var description: String = "",
    var category: List<String> = listOf()
) : Serializable
{
    companion object {
        val entriesList = mutableListOf<TimeSheetEntries>()
        val categories = mutableListOf<String>()
    }
}

