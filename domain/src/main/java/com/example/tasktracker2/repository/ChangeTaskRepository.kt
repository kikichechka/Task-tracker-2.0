package com.example.tasktracker2.repository

import com.example.tasktracker2.Task

interface ChangeTaskRepository {
    suspend fun deleteTask(task: Task)
    suspend fun updateTask(task: Task)
}
