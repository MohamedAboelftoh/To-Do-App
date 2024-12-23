package com.example.domain.usecase

import com.example.domain.model.Task
import com.example.domain.repo.TasksRepo
import javax.inject.Inject

class TasksUseCase @Inject constructor(
    private val tasksRepo: TasksRepo
) {
    fun insertTask (task : Task){
        tasksRepo.insertTask(task)
    }
    fun deleteTask (task : Task){
        tasksRepo.deleteTask(task)
    }
    fun updateTask (task : Task){
        tasksRepo.updateTask(task)
    }
    fun getAllTasks():List<Task>{
        return tasksRepo.getAllTasks()
    }
    fun getAllTasksByDate(date : Long) : List<Task>{
        return tasksRepo.getAllTasksByDate(date)
    }
}