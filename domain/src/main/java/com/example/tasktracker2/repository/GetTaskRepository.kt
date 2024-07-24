package com.example.tasktracker2.repository

import com.example.tasktracker2.model.Task

interface GetTaskRepository {
    suspend fun getActiveTasks() : List<Task>
    suspend fun getCompletedTasks() : List<Task>
}