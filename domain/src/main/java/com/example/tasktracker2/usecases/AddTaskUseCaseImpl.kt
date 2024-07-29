package com.example.tasktracker2.usecases

import com.example.tasktracker2.model.Task
import com.example.tasktracker2.repository.AddTaskRepository
import javax.inject.Inject

class AddTaskUseCaseImpl @Inject constructor(
    private val addTaskRepository: AddTaskRepository
) : AddTaskUseCase {
    override suspend fun add(task: Task) {
        addTaskRepository.addTask(task)
    }
}
