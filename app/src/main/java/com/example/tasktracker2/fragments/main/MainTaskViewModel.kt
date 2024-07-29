package com.example.tasktracker2.fragments.main

import androidx.lifecycle.ViewModel
import com.example.tasktracker2.model.StateTask
import com.example.tasktracker2.model.TaskModel
import com.example.tasktracker2.model.map
import com.example.tasktracker2.usecases.DeleteTaskUseCase
import com.example.tasktracker2.usecases.GetListTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import javax.inject.Inject

class MainTaskViewModel @Inject constructor(
    private val getListTasksUseCase: GetListTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {
    private val _choiceData: MutableStateFlow<StateTask> = MutableStateFlow(StateTask.ACTIVE)
    val choiceData: StateFlow<StateTask> = _choiceData.asStateFlow()

    private val _listData: MutableStateFlow<List<TaskModel>?> = MutableStateFlow(null)
    val listData: StateFlow<List<TaskModel>?> = _listData.asStateFlow()

    private val _listActiveData: MutableStateFlow<List<TaskModel>?> = MutableStateFlow(null)

    private val _listCompletedData: MutableStateFlow<List<TaskModel>?> = MutableStateFlow(null)

    fun changeChoice() = when (_choiceData.value) {
        StateTask.ACTIVE -> _choiceData.value = StateTask.COMPLETED
        StateTask.COMPLETED -> _choiceData.value = StateTask.ACTIVE
    }

    suspend fun updateListTasks() {
        val data = getListTasksUseCase.get().map { it.map() }
        _listData.value = data
        _listActiveData.value = getListActiveTasks()
        _listCompletedData.value = getListCompletedTasks()
    }

    fun getListActiveTasks(): List<TaskModel>? {
        val today = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        return listData.value?.filter {
            calendar.set(
                it.completionDate?.year ?: 0,
                it.completionDate?.monthValue?.minus(1) ?: 0,
                it.completionDate?.dayOfMonth ?: 0,
                it.completionTime?.hour ?: 0,
                it.completionTime?.minute ?: 0
            )
            calendar.timeInMillis > today
        }
    }

    fun getListCompletedTasks(): List<TaskModel>? {
        val today = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        return listData.value?.filter {
            calendar.set(
                it.completionDate?.year ?: 0,
                it.completionDate?.monthValue?.minus(1) ?: 0,
                it.completionDate?.dayOfMonth ?: 0,
                it.completionTime?.hour ?: 0,
                it.completionTime?.minute ?: 0
            )
            calendar.timeInMillis <= today
        }
    }

    fun getTaskByPosition(position: Int) : TaskModel? {
        return when (choiceData.value) {
            StateTask.ACTIVE -> _listActiveData.value?.get(position)
            StateTask.COMPLETED -> _listCompletedData.value?.get(position)
        }
    }

    suspend fun deleteTask(position: Int) {
        when (choiceData.value) {
            StateTask.ACTIVE -> deleteTaskUseCase.delete(_listActiveData.value?.get(position)?.id!!)
            StateTask.COMPLETED -> deleteTaskUseCase.delete(_listCompletedData.value?.get(position)?.id!!)
        }
        updateListTasks()
    }
}
