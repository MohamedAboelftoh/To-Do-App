package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.TaskDto

@Database(entities = [TaskDto :: class], version = 1 , exportSchema = false)
abstract class TasksDataBase : RoomDatabase() {
    abstract fun getDao () : TasksDao
}