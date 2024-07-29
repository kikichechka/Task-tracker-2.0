package com.example.tasktracker2.model

enum class ImportanceModel(val str: String) {
    LOW("Низкая"),
    MEDIUM("Средняя"),
    HIGH("Высокая")
}

fun Importance.mapToModel(): ImportanceModel {
    return when (this) {
        Importance.LOW -> ImportanceModel.LOW
        Importance.MEDIUM -> ImportanceModel.MEDIUM
        Importance.HIGH -> ImportanceModel.HIGH
    }
}

fun ImportanceModel.mapToDomain(): Importance {
    return when (this) {
        ImportanceModel.LOW -> Importance.LOW
        ImportanceModel.MEDIUM -> Importance.MEDIUM
        ImportanceModel.HIGH -> Importance.HIGH
    }
}
