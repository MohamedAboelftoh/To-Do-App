package com.example.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideTasksDataBase(
        @ApplicationContext context: Context
    ): TasksDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            TasksDataBase::class.java,
            "tasks Database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideTasksDao(
        tasksDataBase: TasksDataBase
    ): TasksDao {
        return tasksDataBase.getDao()
    }
}