package com.example.tasktracker2.repository

import com.example.tasktracker2.model.Task

interface ChangeTaskRepository {
    suspend fun deleteTask(id: Int)
    suspend fun updateTask(task: Task)
}
