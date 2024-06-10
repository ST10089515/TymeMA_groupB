package com.example.tymema_v1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class CategoryAdapter(private val listener: RecyclerViewListener) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    private var entries: List<TimeSheetEntries> = emptyList()

    fun updateData(newEntries: List<TimeSheetEntries>) {
        entries = newEntries
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_row_categories, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CategoryAdapter.MyViewHolder, position: Int) {
        val entry = entries[position]
        val categoryName = entry.category.joinToString(", ") // Join categories to a string
        val categoryCount = entries.count { it.category.contains(categoryName) } // Count entries with the same category

        val totalHours = entries.filter { it.category.contains(categoryName) }
            .sumBy { calculateHours(it.startTime, it.endTime) }

        holder.category.text = "$categoryName Category" // Display category name
        holder.numEntries.text = "$categoryCount" // Display the count of entries with the same category
        holder.totalHours.text = "$totalHours" // Display the total hours for this category

        holder.itemView.setOnClickListener{
            listener.onEntryClick(entry, position)
        }
    }

    private fun calculateHours(startTime: String, endTime: String): Int {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

        try {
            val start = sdf.parse(startTime)
            val end = sdf.parse(endTime)

            // Calculate the difference in milliseconds between start and end times
            val diff = end.time - start.time

            // Convert milliseconds to hours
            val hours = TimeUnit.MILLISECONDS.toHours(diff)

            return hours.toInt()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return 0 // Default return value if parsing fails
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category: TextView = itemView.findViewById(R.id.displayCategory2)
        var numEntries: TextView = itemView.findViewById(R.id.numberOfEntries)
        var totalHours: TextView = itemView.findViewById(R.id.totalHours)
    }
    }
