package com.example.tasktracker2.fragments.create

import androidx.lifecycle.ViewModel
import com.example.tasktracker2.model.ImportanceModel
import com.example.tasktracker2.model.TaskModel
import com.example.tasktracker2.model.mapToDomain
import com.example.tasktracker2.usecases.AddTaskUseCase
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class CreateNewTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {

    suspend fun saveNewTask(
        importanceModel: ImportanceModel,
        startDate: LocalDate?,
        startTime: LocalTime?,
        completionDate: LocalDate?,
        completionTime: LocalTime?,
        title: String,
        description: String
    ) {
        val taskModel = TaskModel(
            importance = importanceModel,
            startDate = startDate,
            startTime = startTime,
            completionDate = completionDate,
            completionTime = completionTime,
            title = title,
            description = description
        )
        addTaskUseCase.add(taskModel.mapToDomain())
    }
}
