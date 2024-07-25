package com.example.tasktracker2.fragments.main

import androidx.lifecycle.ViewModel
import com.example.tasktracker2.model.StateTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainTaskViewModel : ViewModel() {
    private val _choiceData: MutableStateFlow<StateTask> = MutableStateFlow(StateTask.ACTIVE)
    val choiceData: StateFlow<StateTask> = _choiceData.asStateFlow()

    fun changeChoice() = when (_choiceData.value) {
        StateTask.ACTIVE -> _choiceData.value = StateTask.COMPLETED
        StateTask.COMPLETED -> _choiceData.value = StateTask.ACTIVE
    }
}
