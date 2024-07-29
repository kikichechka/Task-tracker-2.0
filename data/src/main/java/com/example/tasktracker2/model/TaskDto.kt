package com.example.tasktracker2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "tasks")
data class TaskDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "importance")
    var importanceDto: ImportanceDto,
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

fun TaskDto.mapToModel(): Task {
    return Task(
        id = id,
        importance = importanceDto.mapToModel(),
        startDate = startDate,
        startTime = startTime,
        completionDate = completionDate,
        completionTime = completionTime,
        title = title,
        description = description
    )
}

fun Task.mapToDto(): TaskDto {
    return TaskDto(
        id = id,
        importanceDto = when (importance) {
            Importance.LOW -> ImportanceDto.LOW
            Importance.MEDIUM -> ImportanceDto.MEDIUM
            Importance.HIGH -> ImportanceDto.HIGH
        },
        startDate = startDate,
        startTime = startTime,
        completionDate = completionDate,
        completionTime = completionTime,
        title = title,
        description = description
    )
}
