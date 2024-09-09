package com.example.todoapp1.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task :: class], version = 1 , exportSchema = true)
abstract class ToDoDataBase : RoomDatabase() {
    abstract fun getDao () : Dao
    companion object{
        private var instance : ToDoDataBase ?= null
        fun getInstance(context : Context) : ToDoDataBase{
            if(instance == null)
            {
                instance = Room.databaseBuilder(context.applicationContext,ToDoDataBase::class.java,"task dataBase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}