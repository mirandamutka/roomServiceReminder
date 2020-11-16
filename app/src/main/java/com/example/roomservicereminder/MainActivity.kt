package com.example.roomservicereminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var db : AppDatabase
    private lateinit var job : Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    lateinit var addTaskEditText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        job = Job()

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "toDo-items")
            .fallbackToDestructiveMigration().build()

        addTaskEditText = findViewById(R.id.addTaskEditText)

        val addButton = findViewById<Button>(R.id.addButton)


        addButton.setOnClickListener{
            addToDoItem(Item(0, "${addTaskEditText.text}", false))
        }



    }

    fun addToDoItem(item: Item) {
        launch(Dispatchers.IO) {
            db.itemDao().insert(item)
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }
}