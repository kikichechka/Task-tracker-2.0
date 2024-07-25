package com.example.tasktracker2.model

import java.time.LocalDate
import java.time.LocalTime

class TaskModel(
    val id: Int? = null,
    var importance: ImportanceModel,
    var activity: ActivityModel,
    var startDate: LocalDate?,
    var startTime: LocalTime?,
    var completionDate: LocalDate?,
    var completionTime: LocalTime?,
    var title: String,
    var description: String?,
)

fun Task.map(): TaskModel {
    return TaskModel(
        id = id,
        importance = importance.mapToModel(),
        activity = activity.map(),
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
        activity = activity.mapToDomain(),
        startDate = startDate,
        startTime = startTime,
        completionDate = completionDate,
        completionTime = completionTime,
        title = title,
        description = description
    )
}
