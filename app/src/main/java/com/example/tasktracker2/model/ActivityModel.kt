package com.example.tasktracker2.model

enum class ActivityModel {
    ACTIVE,
    COMPLETED
}

fun Activity.map(): ActivityModel {
    return when (this) {
        Activity.ACTIVE -> ActivityModel.ACTIVE
        Activity.COMPLETED -> ActivityModel.COMPLETED
    }
}

fun ActivityModel.mapToDomain(): Activity {
    return when (this) {
        ActivityModel.ACTIVE -> Activity.ACTIVE
        ActivityModel.COMPLETED -> Activity.COMPLETED
    }
}

