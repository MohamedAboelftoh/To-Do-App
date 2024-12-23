package com.example.todoapp1.ui

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Calendar

fun Calendar.formatTime(): String {
    val formatter = SimpleDateFormat("hh:mm a")
    return formatter.format(time)
}

fun Calendar.formatDate(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(time)
}

fun Calendar.getDateOnly():Long{
    val calendar = Calendar.getInstance()
    calendar.set(
        get(Calendar.YEAR) ,
        get(Calendar.MONTH) ,
        get(Calendar.DAY_OF_MONTH) , 0 , 0 , 0)
    calendar.set(Calendar.MILLISECOND , 0)
    return calendar.time.time
}

fun Calendar.getTimeOnly():Long{
    val calendar = Calendar.getInstance()
    calendar.set(0 , 0 , 0, get(Calendar.HOUR_OF_DAY) , get(Calendar.MINUTE) , 0)
    calendar.set(Calendar.MILLISECOND , 0)
    return calendar.time.time
}