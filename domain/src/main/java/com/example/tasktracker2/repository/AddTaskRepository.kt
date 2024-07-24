package com.example.tasktracker2.repository

import com.example.tasktracker2.Task

interface AddTaskRepository {
    suspend fun addTask(task: Task)
}
