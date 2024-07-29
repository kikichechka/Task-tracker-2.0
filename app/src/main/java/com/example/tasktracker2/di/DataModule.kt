package com.example.tasktracker2.di

import com.example.tasktracker2.db.TasksDao
import com.example.tasktracker2.repository.AddTaskRepository
import com.example.tasktracker2.repository.AddTaskRepositoryImpl
import com.example.tasktracker2.repository.ChangeTaskRepository
import com.example.tasktracker2.repository.ChangeTaskRepositoryImpl
import com.example.tasktracker2.repository.GetTaskRepository
import com.example.tasktracker2.repository.GetTaskRepositoryImpl
import com.example.tasktracker2.usecases.AddTaskUseCase
import com.example.tasktracker2.usecases.ChangeTaskUseCase
import com.example.tasktracker2.usecases.DeleteTaskUseCase
import com.example.tasktracker2.usecases.ChangeTaskUseCaseImpl
import com.example.tasktracker2.usecases.DeleteTaskUseCaseImpl
import com.example.tasktracker2.usecases.GetListTasksUseCaseImpl
import com.example.tasktracker2.usecases.AddTaskUseCaseImpl
import com.example.tasktracker2.usecases.GetListTasksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideGetListTasksUseCase(
        getTaskRepository: GetTaskRepositoryImpl
    ): GetListTasksUseCase {
        return GetListTasksUseCaseImpl(
            getTaskRepository = getTaskRepository
        )
    }

    @Provides
    @Singleton
    fun provideDeleteTasksUseCase(
        changeTaskRepository: ChangeTaskRepositoryImpl
    ): DeleteTaskUseCase {
        return DeleteTaskUseCaseImpl(
            changeTaskRepository = changeTaskRepository
        )
    }

    @Provides
    @Singleton
    fun provideChangeTaskUseCase(
        changeTaskRepository: ChangeTaskRepositoryImpl
    ): ChangeTaskUseCase {
        return ChangeTaskUseCaseImpl(
            changeTaskRepository = changeTaskRepository
        )
    }

    @Provides
    @Singleton
    fun provideAddTaskUseCase(
        addTaskRepository: AddTaskRepositoryImpl
    ): AddTaskUseCase {
        return AddTaskUseCaseImpl(
            addTaskRepository = addTaskRepository
        )
    }

    @Provides
    @Singleton
    fun provideAddTaskRepository(
        tasksDao: TasksDao
    ): AddTaskRepository {
        return AddTaskRepositoryImpl(
            tasksDao = tasksDao
        )
    }

    @Provides
    @Singleton
    fun provideGetTaskRepository(
        tasksDao: TasksDao
    ): GetTaskRepository {
        return GetTaskRepositoryImpl(
            tasksDao = tasksDao
        )
    }

    @Provides
    @Singleton
    fun provideChangeTaskRepository(
        tasksDao: TasksDao
    ): ChangeTaskRepository {
        return ChangeTaskRepositoryImpl(
            tasksDao = tasksDao
        )
    }
}
