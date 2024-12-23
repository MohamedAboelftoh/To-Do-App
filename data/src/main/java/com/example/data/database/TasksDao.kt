package com.example.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.TaskDto

@Dao
interface TasksDao {
    @Insert
    fun insertTask(task: TaskDto)

    @Delete
    fun deleteTask(task: TaskDto)

    @Update
    fun updateTask(task: TaskDto)

    @Query("Select * from TasksTable")
    fun getAllTasks(): List<TaskDto>

    @Query("Select * from TasksTable where date = :date")
    fun getAllTasksByDate(date: Long): List<TaskDto>

}