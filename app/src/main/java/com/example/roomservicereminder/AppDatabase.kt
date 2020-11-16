package com.example.roomservicereminder

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Item::class), version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao() : ItemDao
}