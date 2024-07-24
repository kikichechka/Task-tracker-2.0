package com.example.tasktracker2.usecase.get

import com.example.tasktracker2.model.Task

interface GetActiveTasksUseCase {
    suspend fun get() : List<Task>
}