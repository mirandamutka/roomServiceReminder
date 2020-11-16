package com.example.roomservicereminder

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class ToDoItemRecyclerAdapter (
        val context: Context,
        var list: List<Item>,
    ) : RecyclerView.Adapter<ToDoItemRecyclerAdapter.ViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ToDoItemRecyclerAdapter.ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.todo_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var toDoEntry = list[position]

        holder.itemTextView.text = toDoEntry.task
        holder.checkBox.isChecked = toDoEntry.done
        holder.clickedItemId = toDoEntry.id
        holder.clickedItemPosition = position
    }

    fun removeToDoItem(clickedToDoItemId: Int, clickedItemPosition: Int) {
        val dialogBuilder = AlertDialog.Builder(context)
        var itemToBeDeleted = Item(clickedToDoItemId, "")
        dialogBuilder.setTitle("Remove todo task?")
            .setMessage("Do you want to remove this item?")
            .setPositiveButton("Remove") {dialog, which ->
                (context as MainActivity).deleteToDoItem(itemToBeDeleted)
                DataManager.items.removeAt(clickedItemPosition)

            }
            .setNegativeButton("Cancel") {dialog, which ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()
        alert.show()
    }


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val itemTextView = itemView.findViewById<TextView>(R.id.itemTextView)
        val checkBox = itemView.findViewById<CheckBox>(R.id.checkBox)
        val deleteButton = itemView.findViewById<ImageView>(R.id.deleteButton)
        var clickedItemId = 0
        var clickedItemPosition = 0

        init {
            deleteButton.setOnClickListener{
                removeToDoItem(clickedItemId, clickedItemPosition)

            }
        }
    }
}