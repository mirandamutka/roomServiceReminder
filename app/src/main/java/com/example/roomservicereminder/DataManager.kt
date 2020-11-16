package com.example.roomservicereminder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

object DataManager {
    val items = mutableListOf<Item>()
}