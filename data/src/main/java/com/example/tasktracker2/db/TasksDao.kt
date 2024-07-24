package com.example.tasktracker2.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tasktracker2.model.TaskDto

@Dao
interface TasksDao {

    @Query("SELECT * FROM tasks WHERE activity LIKE 'ACTIVE'")
    suspend fun getActiveTasks() : List<TaskDto>

    @Query("SELECT * FROM tasks WHERE activity LIKE 'COMPLETED'")
    suspend fun getCompletedTasks() : List<TaskDto>

    @Insert(entity = TaskDto::class)
    suspend fun addNewTask(taskDto: TaskDto)

    @Update
    suspend fun updateTask(modifiedTaskDto: TaskDto)

    @Delete
    suspend fun deleteTask(taskDto: TaskDto)
}