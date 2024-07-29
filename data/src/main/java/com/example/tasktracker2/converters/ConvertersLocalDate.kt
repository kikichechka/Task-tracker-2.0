package com.example.tasktracker2.converters

import androidx.room.TypeConverter
import java.time.LocalDate

class ConvertersLocalDate {
    @TypeConverter
    fun fromLocalDate(localDate: LocalDate?): String? {
        return if (localDate == null) null else "$localDate"
    }

    @TypeConverter
    fun toLocation(str: String?): LocalDate? {
        return if (str == null) {
            null
        } else {
            val list = str.split("-")
            LocalDate.of(list[0].toInt(), list[1].toInt(), list[2].toInt())
        }
    }
}
