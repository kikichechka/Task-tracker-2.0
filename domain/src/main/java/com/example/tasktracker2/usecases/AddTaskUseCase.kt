package com.example.tasktracker2.usecases

import com.example.tasktracker2.model.Task

interface AddTaskUseCase {
    suspend fun add(task: Task)
}
