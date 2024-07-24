package com.example.tasktracker2

import java.time.LocalDate
import java.time.LocalTime

class Task (
    val id: Int,
    var importance: Importance,
    var activity: Activity = Activity.ACTIVE,
    var startDate: LocalDate?,
    var startTime: LocalTime?,
    var completionDate: LocalDate?,
    var completionTime: LocalTime?,
    var title: String,
    var description: String?,
)