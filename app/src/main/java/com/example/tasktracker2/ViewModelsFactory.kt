package com.example.tasktracker2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tasktracker2.fragments.change.ChangeTaskViewModel
import com.example.tasktracker2.fragments.create.CreateNewTaskViewModel
import com.example.tasktracker2.fragments.main.MainTaskViewModel
import javax.inject.Inject

class ViewModelsFactory @Inject constructor(
    private val createNewTaskViewModel: CreateNewTaskViewModel,
    private val mainTaskViewModel: MainTaskViewModel,
    private val changeTaskViewModel: ChangeTaskViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateNewTaskViewModel::class.java)) {
            return createNewTaskViewModel as T
        }
        if (modelClass.isAssignableFrom(MainTaskViewModel::class.java)) {
            return mainTaskViewModel as T
        }
        if (modelClass.isAssignableFrom(ChangeTaskViewModel::class.java)) {
            return changeTaskViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
