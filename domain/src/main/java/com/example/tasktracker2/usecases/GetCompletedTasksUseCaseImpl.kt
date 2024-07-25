package com.example.tasktracker2.usecases

import com.example.tasktracker2.model.Task
import com.example.tasktracker2.repository.GetTaskRepository
import com.example.tasktracker2.usecase.get.GetCompletedTasksUseCase
import javax.inject.Inject

class GetCompletedTasksUseCaseImpl @Inject constructor(
    private val getTaskRepository: GetTaskRepository
) : GetCompletedTasksUseCase {
    override suspend fun get(): List<Task> {
        return getTaskRepository.getCompletedTasks()
    }
}
