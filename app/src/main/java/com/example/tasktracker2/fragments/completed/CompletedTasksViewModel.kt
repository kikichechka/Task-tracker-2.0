package com.example.tasktracker2.fragments.completed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasktracker2.model.TaskModel
import com.example.tasktracker2.model.map
import com.example.tasktracker2.usecase.get.GetCompletedTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompletedTasksViewModel @Inject constructor(
    private val getCompletedTasksUseCase: GetCompletedTasksUseCase,
) : ViewModel() {
    private val _listData: MutableStateFlow<List<TaskModel>?> = MutableStateFlow(null)
    val listData: StateFlow<List<TaskModel>?> = _listData.asStateFlow()

    init {
        viewModelScope.launch {
            _listData.value = getCompletedTasksUseCase.get().map { it.map() }
        }
    }

    suspend fun updateData() {
        val data = getCompletedTasksUseCase.get().map { it.map() }
        _listData.value = data
    }
}
