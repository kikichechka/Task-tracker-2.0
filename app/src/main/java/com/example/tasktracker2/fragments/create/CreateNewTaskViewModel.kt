package com.example.tasktracker2.fragments.create

import androidx.lifecycle.ViewModel
import com.example.tasktracker2.usecase.add.AddTaskUseCase
import javax.inject.Inject

class CreateNewTaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase
): ViewModel() {
}