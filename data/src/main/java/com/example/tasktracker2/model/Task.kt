package com.example.tasktracker2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "importance")
    var importance: Importance,
    @ColumnInfo(name = "activity")
    var activity: Boolean = true,
    @ColumnInfo(name = "start_date")
    var startDate: LocalDate?,
    @ColumnInfo(name = "start_time")
    var startTime: LocalTime?,
    @ColumnInfo(name = "completion_date")
    var completionDate: LocalDate?,
    @ColumnInfo(name = "completion_time")
    var completionTime: LocalTime?,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String?,
)
