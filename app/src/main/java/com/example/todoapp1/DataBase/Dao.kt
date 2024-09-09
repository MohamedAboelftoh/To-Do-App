package com.example.todoapp1.DataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Dao {
    @Insert
    fun insertTask (task : Task)
    @Delete
    fun deleteTask (task : Task)
    @Update
    fun updateTask (task : Task)
    @Query("Select * from TaskTable")
    fun getAllTasks () : List<Task>
    @Query("Select * from TaskTable where time = :date")
    fun getAllTasksByDate(date : Long) : List<Task>

}