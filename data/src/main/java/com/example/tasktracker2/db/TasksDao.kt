package com.example.tasktracker2.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tasktracker2.model.TaskDto

@Dao
interface TasksDao {

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<TaskDto>

    @Insert(entity = TaskDto::class)
    suspend fun addNewTask(taskDto: TaskDto)

    @Update
    suspend fun updateTask(modifiedTaskDto: TaskDto)

    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteTask(id: Int)
}
