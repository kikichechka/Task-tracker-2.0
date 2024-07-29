package com.example.tasktracker2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class TaskModel(
    val id: Int? = null,
    var importance: ImportanceModel,
    var startDate: LocalDate?,
    var startTime: LocalTime?,
    var completionDate: LocalDate?,
    var completionTime: LocalTime?,
    var title: String,
    var description: String?,
): Parcelable

fun Task.map(): TaskModel {
    return TaskModel(
        id = id,
        importance = importance.mapToModel(),
        startDate = startDate,
        startTime = startTime,
        completionDate = completionDate,
        completionTime = completionTime,
        title = title,
        description = description
    )
}

fun TaskModel.mapToDomain(): Task {
    return Task(
        id= id,
        importance = importance.mapToDomain(),
        startDate = startDate,
        startTime = startTime,
        completionDate = completionDate,
        completionTime = completionTime,
        title = title,
        description = description
    )
}
