package com.example.tasktracker2.usecases

import com.example.tasktracker2.model.Task

interface GetListTasksUseCase {
    suspend fun get() : List<Task>
}
