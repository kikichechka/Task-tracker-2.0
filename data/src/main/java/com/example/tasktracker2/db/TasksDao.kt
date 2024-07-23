package com.example.tasktracker2.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tasktracker2.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Query("SELECT * FROM tasks WHERE activity LIKE 'ACTIVE'")
    suspend fun getActiveTasks() : List<Task>

    @Query("SELECT * FROM tasks WHERE activity LIKE 'COMPLETED'")
    suspend fun getCompletedTasks() : List<Task>

    @Insert(entity = Task::class)
    suspend fun addNewTask(task: Task)

    @Update
    suspend fun updateTask(modifiedTask: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}