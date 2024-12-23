package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val id : Int ?= null ,
    var title : String ?= null ,
    var description : String ?= null ,
    var isDone : Boolean = false,
    var time : Long ?= null ,
    var date : Long ?= null
):Parcelable
