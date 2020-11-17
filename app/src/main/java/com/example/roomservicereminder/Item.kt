package com.example.roomservicereminder

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
        @PrimaryKey(autoGenerate = true) val id: Int,
        @ColumnInfo(name = "task") var task: String?,
        @ColumnInfo(name = "done") var done: Boolean = false
)