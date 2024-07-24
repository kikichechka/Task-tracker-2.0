package com.example.tasktracker2.repository

import com.example.tasktracker2.model.Task

interface ChangeTaskRepository {
    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task)
}
