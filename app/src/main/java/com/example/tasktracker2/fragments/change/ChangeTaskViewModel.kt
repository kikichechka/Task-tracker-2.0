package com.example.tasktracker2.fragments.change

import androidx.lifecycle.ViewModel
import com.example.tasktracker2.model.TaskModel
import com.example.tasktracker2.model.mapToDomain
import com.example.tasktracker2.usecases.ChangeTaskUseCase
import javax.inject.Inject

class ChangeTaskViewModel @Inject constructor(
    private val changeTaskUseCase: ChangeTaskUseCase
) : ViewModel() {

    suspend fun changeTask(taskModel: TaskModel) {
        changeTaskUseCase.change(taskModel.mapToDomain())
    }
}
