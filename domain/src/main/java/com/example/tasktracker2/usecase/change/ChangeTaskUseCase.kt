package com.example.tasktracker2.usecase.change

import com.example.tasktracker2.model.Task

interface ChangeTaskUseCase {
    suspend fun change(task: Task)
}