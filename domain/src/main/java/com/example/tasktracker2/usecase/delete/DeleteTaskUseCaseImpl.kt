package com.example.tasktracker2.usecase.delete

import com.example.tasktracker2.model.Task
import com.example.tasktracker2.repository.ChangeTaskRepository
import javax.inject.Inject

class DeleteTaskUseCaseImpl @Inject constructor(
    private val changeTaskRepository: ChangeTaskRepository
) : DeleteTaskUseCase {
    override suspend fun delete(task: Task) {
        changeTaskRepository.deleteTask(task)
    }
}
