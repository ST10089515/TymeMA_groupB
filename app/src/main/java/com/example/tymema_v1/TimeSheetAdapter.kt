package com.example.tymema_v1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TimeSheetAdapter(private val listener: RecyclerViewListener) :
    RecyclerView.Adapter<TimeSheetAdapter.MyViewHolder>() {

    private var entries: List<TimeSheetEntries> = emptyList()

    fun updateData(newEntries: List<TimeSheetEntries>) {
        entries = newEntries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entry = entries[position]
        val tempViewHolder = MyViewHolder(holder.itemView)
        holder.category.text = entry.category.joinToString(", ") // Displaying categories
        holder.description.text = entry.description // Displaying description
        holder.calcTime.text = tempViewHolder.calculateTime(entry.startTime, entry.endTime) // Displaying calculated time

        holder.itemView.setOnClickListener{
            listener.onEntryClick(entries[position], position)
        }
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category: TextView = itemView.findViewById(R.id.displayCategory)
        var description: TextView = itemView.findViewById(R.id.showDesc)
        var calcTime: TextView = itemView.findViewById(R.id.showTime)

        // Function to calculate time duration
        public fun calculateTime(startTime: String, endTime: String): String {
            // logic to calculate time duration (endTime - startTime)
            // startTime and endTime are in HH:mm format
            val startHour = startTime.split(":")[0].toInt()
            val startMinute = startTime.split(":")[1].toInt()
            val endHour = endTime.split(":")[0].toInt()
            val endMinute = endTime.split(":")[1].toInt()

            val totalStartMinutes = startHour * 60 + startMinute
            val totalEndMinutes = endHour * 60 + endMinute
            val totalTimeMinutes = totalEndMinutes - totalStartMinutes

            val hours = totalTimeMinutes / 60
            val minutes = totalTimeMinutes % 60

            return String.format("%02d:%02d", hours, minutes)
        }
    }
}