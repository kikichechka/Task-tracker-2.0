package com.example.tasktracker2.usecases

import com.example.tasktracker2.model.Task

interface ChangeTaskUseCase {
    suspend fun change(task: Task)
}
