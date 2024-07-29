package com.example.tasktracker2.repository

import com.example.tasktracker2.model.Task

interface GetTaskRepository {
    suspend fun getListTasks() : List<Task>
}
