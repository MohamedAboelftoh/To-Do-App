package com.example.data.repo

import com.example.domain.repo.TasksRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class Di {
    @Binds
    abstract fun provideTasksRepo(
        tasksRepoImpl: TasksRepoImpl
    ): TasksRepo
}