package com.example.todoapp1.ui.home.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import com.example.todoapp1.DataBase.Task
import com.example.todoapp1.DataBase.ToDoDataBase
import com.example.todoapp1.R
import com.example.todoapp1.databinding.FragmentAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class AddTaskFragment : BottomSheetDialogFragment() {
    lateinit var viewBinding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnAdd.setOnClickListener {
            createTask()
            onTaskAddedListener?.onTaskAdded()
            dismiss()
        }
        viewBinding.dateContainer.setOnClickListener {
            showDateBickerDialog()
        }
    }

    var calendar = Calendar.getInstance()

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDateBickerDialog() {
        context?.let {
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

    private fun createTask() {
        if (!validation()) {
            return
        }
        val task = Task(
            title = viewBinding.title.text.toString(),
            description = viewBinding.description.text.toString(),
            time = calendar.timeInMillis
        )
        ToDoDataBase.getInstance(requireContext()).getDao().insertTask(task)
    }

    var onTaskAddedListener: OnTaskAddedListener? = null

    interface OnTaskAddedListener {
        fun onTaskAdded()
    }
}