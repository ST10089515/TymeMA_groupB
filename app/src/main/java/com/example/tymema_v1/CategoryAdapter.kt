package com.example.tymema_v1

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(private val entries: List<TimeSheetEntries>, private val listener: RecyclerViewListener) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

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

        holder.category.text = "$categoryName Category" // Display category name
        holder.numEntries.text = "$categoryCount" // Display the count of entries with the same category

        holder.itemView.setOnClickListener{
            listener.onEntryClick(entry, position)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category: TextView = itemView.findViewById(R.id.displayCategory2)
        var numEntries: TextView = itemView.findViewById(R.id.numberOfEntries)

        }
    }
