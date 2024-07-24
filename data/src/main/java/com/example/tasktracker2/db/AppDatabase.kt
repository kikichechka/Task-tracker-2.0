package com.example.tasktracker2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tasktracker2.converters.ConvertersBoolean
import com.example.tasktracker2.converters.ConvertersLocalDate
import com.example.tasktracker2.converters.ConvertersLocalTime
import com.example.tasktracker2.model.TaskDto

@Database(entities = [TaskDto::class], version = 1)
@TypeConverters(
    ConvertersLocalDate::class,
    ConvertersLocalTime::class,
    ConvertersBoolean::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao
}