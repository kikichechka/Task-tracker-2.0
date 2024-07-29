package com.example.tasktracker2.usecases

import com.example.tasktracker2.repository.ChangeTaskRepository
import javax.inject.Inject

class DeleteTaskUseCaseImpl @Inject constructor(
    private val changeTaskRepository: ChangeTaskRepository
) : DeleteTaskUseCase {
    override suspend fun delete(id: Int) {
        changeTaskRepository.deleteTask(id)
    }
}
