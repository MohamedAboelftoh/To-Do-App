package com.example.data.datasource.online

import com.example.data.model.TaskDto

interface TasksDataSource {
    fun insertTask(task: TaskDto)
    fun deleteTask(task: TaskDto)
    fun updateTask(task: TaskDto)
    fun getAllTasks(): List<TaskDto>
    fun getAllTasksByDate(date: Long): List<TaskDto>
}