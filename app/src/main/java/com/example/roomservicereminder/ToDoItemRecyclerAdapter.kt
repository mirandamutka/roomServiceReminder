package com.example.roomservicereminder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDoItemRecyclerAdapter (
        val context: Context,
        var list: List<Item>
    ) : RecyclerView.Adapter<ToDoItemRecyclerAdapter.ViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ToDoItemRecyclerAdapter.ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.todo_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var toDoEntry = list[position]

        holder.itemTextView.text = toDoEntry.task
        holder.checkBox.isChecked = toDoEntry.done
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val itemTextView = itemView.findViewById<TextView>(R.id.itemTextView)
        val checkBox = itemView.findViewById<CheckBox>(R.id.checkBox)
    }
}