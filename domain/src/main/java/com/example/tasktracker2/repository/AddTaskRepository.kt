package com.example.tasktracker2.repository

import com.example.tasktracker2.model.Task

interface AddTaskRepository {
    suspend fun addTask(task: Task)
}
