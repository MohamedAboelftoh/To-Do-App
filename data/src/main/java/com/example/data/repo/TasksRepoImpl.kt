package com.example.data.repo

import com.example.data.datasource.online.TasksDataSource
import com.example.data.model.TaskDto
import com.example.domain.model.Task
import com.example.domain.repo.TasksRepo
import javax.inject.Inject

class TasksRepoImpl @Inject constructor(
    private val tasksDataSource: TasksDataSource
) : TasksRepo {
    override  fun insertTask(task: Task) {
        try {
            val taskDto = TaskDto(
                id = task.id,
                title = task.title,
                description = task.description,
                isDone = task.isDone,
                date = task.date,
                time = task.time
            )
            tasksDataSource.insertTask(taskDto)
        } catch (ex: Exception) {
            throw ex
        }
    }

    override  fun deleteTask(task: Task) {
        try {
            val taskDto = TaskDto(
                id = task.id,
                title = task.title,
                description = task.description,
                isDone = task.isDone,
                date = task.date,
                time = task.time
            )
            tasksDataSource.deleteTask(taskDto)
        } catch (ex: Exception) {
            throw ex
        }
    }

    override  fun updateTask(task: Task) {
        try {
            val taskDto = TaskDto(
                id = task.id,
                title = task.title,
                description = task.description,
                isDone = task.isDone,
                date = task.date,
                time = task.time
            )
            tasksDataSource.updateTask(taskDto)
        } catch (ex: Exception) {
            throw ex
        }
    }

    override  fun getAllTasks(): List<Task> {
        try {
            return tasksDataSource.getAllTasks().map { it.toTask() }
        } catch (ex: Exception) {
            throw ex
        }
    }

    override  fun getAllTasksByDate(date: Long): List<Task> {
        try {
            return tasksDataSource.getAllTasksByDate(date).map { it.toTask() }
        } catch (ex: Exception) {
            throw ex
        }
    }
}