package com.example.tasktracker2.converters

import androidx.room.TypeConverter
import java.time.LocalTime

class ConvertersLocalTime {
    @TypeConverter
    fun fromLocalTime(localTime: LocalTime?): String? {
        return if (localTime == null) null else "$localTime"
    }

    @TypeConverter
    fun toLocation(str: String?): LocalTime? {
        return if (str == null) {
            null
        } else {
            val list = str.split(":")
            return LocalTime.of(list[0].toInt(), list[1].toInt())
        }
    }
}
