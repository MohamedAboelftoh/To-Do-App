package com.example.data.datasource.online

import com.example.data.database.TasksDao
import com.example.data.model.TaskDto
import javax.inject.Inject

class TasksDataSourceImpl @Inject constructor(
    private val tasksDao: TasksDao
) : TasksDataSource {
    override  fun insertTask(task: TaskDto) {
        tasksDao.insertTask(task)
    }

    override  fun deleteTask(task: TaskDto) {
        tasksDao.deleteTask(task)
    }

    override  fun updateTask(task: TaskDto) {
        tasksDao.updateTask(task)
    }

    override  fun getAllTasks(): List<TaskDto> {
        return tasksDao.getAllTasks()
    }

    override  fun getAllTasksByDate(date: Long): List<TaskDto> {
        return tasksDao.getAllTasksByDate(date)
    }
}