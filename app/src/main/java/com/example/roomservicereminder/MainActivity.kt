package com.example.roomservicereminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var db : AppDatabase
    private lateinit var job : Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    lateinit var recyclerView : RecyclerView
    lateinit var addTaskEditText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = Job()

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "toDo-items")
            .fallbackToDestructiveMigration().build()

        val loadedItems = loadToDoItems()

        launch(Dispatchers.Main) {
            val itemsList = loadedItems.await()

            for(item in itemsList) {
                DataManager.items.add(item)
            }
        }

        addTaskEditText = findViewById(R.id.addTaskEditText)

        val addButton = findViewById<Button>(R.id.addButton)


        addButton.setOnClickListener{
            if(addTaskEditText?.text.toString() != "") {
                addToDoItem(Item(0, "${addTaskEditText.text}", false))
            }

        }

        recyclerView = findViewById<RecyclerView>(R.id.toDoItemRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = ToDoItemRecyclerAdapter(this, DataManager.items)

    }

    fun addToDoItem(item: Item) {
        launch(Dispatchers.IO) {
            db.itemDao().insert(item)
            DataManager.items.add(item)
        }
        recyclerView.adapter?.notifyDataSetChanged()
    }

    fun loadToDoItems() : Deferred<List<Item>> =
        async(Dispatchers.IO) {
            db.itemDao().getAll()

    }

    fun deleteToDoItem(item: Item) {
        launch(Dispatchers.IO) {
            db.itemDao().delete(item)
        }
        recyclerView.adapter?.notifyDataSetChanged()

    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}