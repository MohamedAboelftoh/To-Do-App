package com.example.todoapp1

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.todoapp1.DataBase.Task
import com.example.todoapp1.DataBase.ToDoDataBase
import com.example.todoapp1.databinding.ActivityUpdateBinding
import com.example.todoapp1.ui.home.HomeActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DetailsActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityUpdateBinding
    lateinit var task: Task

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        task = ((intent.getSerializableExtra("task") as? Task)!!)
        calendar.timeInMillis = task.time!!
        viewBinding.back.setOnClickListener {
            finish()
        }
        getDataFromTaskItem()
        viewBinding.btnSave.setOnClickListener {
            updateTask()
        }
        viewBinding.date.setOnClickListener {
            showDateBickerDialog()
        }
    }

    private fun updateTask() {
        if (!validation()) {
            return
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        task.title = viewBinding.title.text.toString()
        task.description = viewBinding.description.text.toString()
        task.time = calendar.timeInMillis
        ToDoDataBase.getInstance(this).getDao()
            .updateTask(task)
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun getDataFromTaskItem() {
        val title = task.title
        val description = task.description
        val time = task.time
        val timeConverted = time?.let { convertLongToTime(it) }
        viewBinding.title.setText(title)
        viewBinding.description.setText(description)
        viewBinding.date.text = timeConverted
    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd-MM-yyyy")
        return format.format(date)
    }

    private fun validation(): Boolean {
        var isVaild: Boolean = true
        if (viewBinding.title.text.isNullOrBlank()) {
            viewBinding.titleContainer.error = "Title is require"
            isVaild = false
        } else {
            viewBinding.titleContainer.error = null
        }
        if (viewBinding.description.text.isNullOrBlank()) {
            viewBinding.descriptionContainer.error = "Description is require"
            isVaild = false
        } else {
            viewBinding.descriptionContainer.error = null
        }
        if (viewBinding.date.text.isNullOrBlank()) {
            viewBinding.dateContainer.error = "Date is require"
            isVaild = false
        } else {
            viewBinding.dateContainer.error = null
        }
        return isVaild
    }

    var calendar = Calendar.getInstance()

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDateBickerDialog() {
        this.let {
            val dialog = DatePickerDialog(it)
            dialog.setOnDateSetListener { p0, year, month, day ->
                viewBinding.date.text = "$day-${month + 1}-$year"
                calendar.set(year, month, day)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
            }
            dialog.show()
        }
    }
}