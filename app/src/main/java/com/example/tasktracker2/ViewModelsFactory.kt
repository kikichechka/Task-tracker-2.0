package com.example.tasktracker2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tasktracker2.fragments.active.ActiveTasksViewModel
import com.example.tasktracker2.fragments.completed.CompletedTasksViewModel
import javax.inject.Inject

class ViewModelsFactory @Inject constructor(
    private val activeTasksViewModel: ActiveTasksViewModel,
    private val completedTasksViewModel: CompletedTasksViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ActiveTasksViewModel::class.java)) {
            return activeTasksViewModel as T
        }
        if (modelClass.isAssignableFrom(CompletedTasksViewModel::class.java)) {
            return completedTasksViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
