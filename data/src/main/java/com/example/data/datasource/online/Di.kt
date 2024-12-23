package com.example.data.datasource.online

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class Di {
    @Binds
    abstract fun provideTasksDataSource(
        tasksDataSourceImpl: TasksDataSourceImpl
    ):TasksDataSource
}