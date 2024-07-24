package com.example.tasktracker2.repository

import com.example.tasktracker2.Task
import com.example.tasktracker2.db.TasksDao
import com.example.tasktracker2.model.mapToDto
import javax.inject.Inject

class ChangeTaskRepositoryImpl @Inject constructor(
    private val tasksDao: TasksDao
) : ChangeTaskRepository {
    override suspend fun deleteTask(task: Task) {
        tasksDao.deleteTask(task.mapToDto())
    }

    override suspend fun updateTask(task: Task) {
        tasksDao.updateTask(task.mapToDto())
    }
}
