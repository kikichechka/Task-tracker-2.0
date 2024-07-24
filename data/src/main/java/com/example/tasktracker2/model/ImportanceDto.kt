package com.example.tasktracker2.model

enum class ImportanceDto {
    LOW {
        override fun mapToModel(): Importance {
            return Importance.LOW
        }
    },
    MEDIUM {
        override fun mapToModel(): Importance {
            return Importance.MEDIUM
        }
    },
    HIGH {
        override fun mapToModel(): Importance {
            return Importance.HIGH
        }
    };

    abstract fun mapToModel(): Importance
}
