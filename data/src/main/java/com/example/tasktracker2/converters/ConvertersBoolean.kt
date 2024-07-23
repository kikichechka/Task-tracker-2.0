package com.example.tasktracker2.converters

import androidx.room.TypeConverter

class ConvertersBoolean {
    @TypeConverter
    fun toInt(data: Boolean): Int {
        return if (data) 1 else 0
    }

    @TypeConverter
    fun toBoolean(data: Int): Boolean {
        return data != 0
    }
}
