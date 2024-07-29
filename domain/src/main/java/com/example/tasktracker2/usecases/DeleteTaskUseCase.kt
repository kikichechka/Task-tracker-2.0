package com.example.tasktracker2.usecases

interface DeleteTaskUseCase {
    suspend fun delete(id: Int)
}
