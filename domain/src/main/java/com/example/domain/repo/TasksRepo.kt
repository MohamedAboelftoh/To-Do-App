package com.example.domain.repo

import com.example.domain.model.Task

interface TasksRepo {
    fun insertTask(task: Task)
    fun deleteTask(task: Task)
    fun updateTask(task: Task)
    fun getAllTasks(): List<Task>
    fun getAllTasksByDate(date: Long): List<Task>
}