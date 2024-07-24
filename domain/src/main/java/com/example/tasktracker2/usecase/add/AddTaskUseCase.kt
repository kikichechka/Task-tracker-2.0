package com.example.tasktracker2.usecase.add

import com.example.tasktracker2.model.Task

interface AddTaskUseCase {
    suspend fun add(task: Task)
}