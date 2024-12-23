package com.example.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Task
import java.io.Serializable

@Entity(tableName = "TasksTable")
data class TaskDto(
    @PrimaryKey(autoGenerate = true)
    val id : Int ?= null ,
    @ColumnInfo
    var title : String ?= null ,
    @ColumnInfo
    var description : String ?= null ,
    @ColumnInfo
    var isDone : Boolean = false,
    @ColumnInfo
    var time : Long ?= null ,
    @ColumnInfo
    var date : Long ?= null
):Serializable{
    fun toTask(): Task {
        return Task(id, title, description, isDone, time, date)
    }
}
