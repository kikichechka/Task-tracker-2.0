package com.example.tasktracker2.fragments.active

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktracker2.model.TaskModel
import com.example.tasktracker2.model.map
import com.example.tasktracker2.model.mapToDomain
import com.example.tasktracker2.usecase.add.AddTaskUseCase
import com.example.tasktracker2.usecases.AddTaskUseCaseImpl
import com.example.tasktracker2.usecases.GetActiveTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.PrimitiveIterator
import javax.inject.Inject

class ActiveTasksViewModel @Inject constructor(
    private val getActiveTasksUseCase: GetActiveTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase
) : ViewModel() {
    private val _listData: MutableStateFlow<List<TaskModel>?> = MutableStateFlow(null)
    val listData: StateFlow<List<TaskModel>?> = _listData.asStateFlow()

    suspend fun updateData() {
        val data = getActiveTasksUseCase.get().map { it.map() }
        _listData.value = data
    }

    suspend fun add(taskModel: TaskModel) {
        addTaskUseCase.add(taskModel.mapToDomain())
    }
}
