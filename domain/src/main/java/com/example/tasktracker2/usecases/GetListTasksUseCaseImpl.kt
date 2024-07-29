package com.example.tasktracker2.usecases

import com.example.tasktracker2.model.Task
import com.example.tasktracker2.repository.GetTaskRepository
import javax.inject.Inject

class GetListTasksUseCaseImpl @Inject constructor(
    private val getTaskRepository: GetTaskRepository
) : GetListTasksUseCase {
    override suspend fun get(): List<Task> {
        return getTaskRepository.getListTasks()
    }
}
