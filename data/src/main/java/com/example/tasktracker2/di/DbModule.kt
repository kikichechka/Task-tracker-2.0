package com.example.tasktracker2.di

import android.content.Context
import androidx.room.Room
import com.example.tasktracker2.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "database"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: AppDatabase) = db.tasksDao()
}
