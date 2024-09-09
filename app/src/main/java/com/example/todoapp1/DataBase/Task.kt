package com.example.todoapp1.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "TaskTable")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id : Int ?= null ,
    @ColumnInfo
    var title : String ?= null ,
    @ColumnInfo
    var description : String ?= null ,
    @ColumnInfo
    var isDone : Boolean = false,
    @ColumnInfo
    var time : Long ?= null
):Serializable
