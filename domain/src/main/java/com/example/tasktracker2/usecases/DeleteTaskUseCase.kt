package com.example.tasktracker2.usecase.delete

import com.example.tasktracker2.model.Task

interface DeleteTaskUseCase {
    suspend fun delete(task: Task)
}