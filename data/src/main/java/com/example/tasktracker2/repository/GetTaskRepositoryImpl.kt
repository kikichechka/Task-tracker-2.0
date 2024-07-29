package com.example.tasktracker2.repository

import com.example.tasktracker2.model.Task
import com.example.tasktracker2.db.TasksDao
import com.example.tasktracker2.model.mapToModel
import javax.inject.Inject

class GetTaskRepositoryImpl @Inject constructor(private val tasksDao: TasksDao) :
    GetTaskRepository {
    override suspend fun getListTasks(): List<Task> {
        return tasksDao.getAllTasks().map { it.mapToModel() }
    }
}
