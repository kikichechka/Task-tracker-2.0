package com.example.tasktracker2.usecase.get

import com.example.tasktracker2.model.Task
import com.example.tasktracker2.repository.GetTaskRepository
import javax.inject.Inject

class GetActiveTasksUseCaseImpl @Inject constructor(
    private val getTaskRepository: GetTaskRepository
) : GetActiveTasksUseCase {
    override suspend fun get(): List<Task> {
        return getTaskRepository.getActiveTasks()
    }
}
