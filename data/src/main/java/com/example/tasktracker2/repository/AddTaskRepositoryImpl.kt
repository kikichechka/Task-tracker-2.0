package com.example.tasktracker2.repository

import com.example.tasktracker2.model.Task
import com.example.tasktracker2.db.TasksDao
import com.example.tasktracker2.model.mapToDto
import javax.inject.Inject

class AddTaskRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao
) : AddTaskRepository {
    override suspend fun addTask(task: Task) {
        tasksDao.addNewTask(task.mapToDto())
    }
}
