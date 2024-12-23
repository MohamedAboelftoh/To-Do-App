package com.example.todoapp1.ui.datails

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.domain.model.Task
import com.example.todoapp1.databinding.ActivityUpdateBinding
import com.example.todoapp1.ui.home.HomeActivity
import com.example.todoapp1.ui.formatDate
import com.example.todoapp1.ui.formatTime
import com.example.todoapp1.ui.getDateOnly
import com.example.todoapp1.ui.getTimeOnly
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityUpdateBinding
    private lateinit var task : Task
    private var calendar = Calendar.getInstance()
    private lateinit var viewModel : DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        task = intent.extras?.getParcelable("task") ?: Task()
        setupViews()
    }

    private fun setupViews() {
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
        viewBinding.time.setOnClickListener {
            showTimeBikerDialog()
        }
    }

    private fun updateTask() {
        if (!validation()) {
            return
        }
        task.title = viewBinding.title.text.toString()
        task.description = viewBinding.description.text.toString()
        task.date = calendar.getDateOnly()
        task.time = calendar.getTimeOnly()
        viewModel.updateTask(task)
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun getDataFromTaskItem() {
        viewBinding.title.setText(task.title)
        viewBinding.description.setText(task.description)
        viewBinding.date.setText(formatDate(task.date!!))
        viewBinding.time.setText(formatTime(task.time!!))
    }

    private fun validation(): Boolean {
        var isValid: Boolean = true
        if (viewBinding.title.text.isNullOrBlank()) {
            viewBinding.titleContainer.error = "Title is require"
            isValid = false
        } else {
            viewBinding.titleContainer.error = null
        }
        if (viewBinding.description.text.isNullOrBlank()) {
            viewBinding.descriptionContainer.error = "Description is require"
            isValid = false
        } else {
            viewBinding.descriptionContainer.error = null
        }
        if (viewBinding.date.text.isNullOrBlank()) {
            viewBinding.dateContainer.error = "Date is require"
            isValid = false
        } else {
            viewBinding.dateContainer.error = null
        }
        return isValid
    }


    @SuppressLint("SetTextI18n")
    private fun showDateBickerDialog() {
        this.let {
            val dialog = DatePickerDialog(it)
            dialog.setOnDateSetListener { p0, year, month, day ->
                calendar.set(Calendar.DAY_OF_MONTH, day)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.YEAR, year)
                viewBinding.date.setText(calendar.formatDate())
            }
            dialog.show()
        }
    }

    private fun showTimeBikerDialog() {
        val timePickerDialog = TimePickerDialog(
            this,
            { timePicker, hours, minutes ->
                calendar.set(Calendar.HOUR_OF_DAY, hours)
                calendar.set(Calendar.MINUTE, minutes)
                viewBinding.time.setText(calendar.formatTime())
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()
    }

    private fun formatTime(time : Long): String {
        val formatter = SimpleDateFormat("hh:mm a")
        return formatter.format(time)
    }

    private fun formatDate(date : Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(date)
    }
}