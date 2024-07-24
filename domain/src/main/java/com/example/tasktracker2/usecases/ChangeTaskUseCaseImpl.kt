package com.example.tasktracker2.usecase.change

import com.example.tasktracker2.model.Task
import com.example.tasktracker2.repository.ChangeTaskRepository
import javax.inject.Inject

class ChangeTaskUseCaseImpl @Inject constructor(
    private val changeTaskRepository: ChangeTaskRepository
) : ChangeTaskUseCase {
    override suspend fun change(task: Task) {
        changeTaskRepository.updateTask(task)
    }
}
