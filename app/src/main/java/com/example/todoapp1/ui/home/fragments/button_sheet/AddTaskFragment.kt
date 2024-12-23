package com.example.todoapp1.ui.home.fragments.button_sheet

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.domain.model.Task
import com.example.todoapp1.databinding.FragmentAddTaskBinding
import com.example.todoapp1.ui.formatDate
import com.example.todoapp1.ui.formatTime
import com.example.todoapp1.ui.getDateOnly
import com.example.todoapp1.ui.getTimeOnly
import com.example.todoapp1.ui.home.fragments.tasks_list.ListFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
@AndroidEntryPoint
class AddTaskFragment : BottomSheetDialogFragment() {
    private lateinit var viewBinding: FragmentAddTaskBinding
    private lateinit var viewModel : AddTaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AddTaskViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        viewBinding.btnAdd.setOnClickListener {
            if (!validation()) {
                return@setOnClickListener
            }
            insertTask()
            onTaskAddedListener?.onTaskAdded()
            dismiss()
        }
        viewBinding.date.setOnClickListener {
            showDateBickerDialog()
        }
        viewBinding.time.setOnClickListener {
            showTimeBikerDialog()
        }
    }

    private fun showTimeBikerDialog() {
        val timePickerDialog = TimePickerDialog(
            requireContext(),
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

    private var calendar = Calendar.getInstance()

    @SuppressLint("SetTextI18n")
    private fun showDateBickerDialog() {
        val dateBickerDialog = DatePickerDialog(requireContext())
        dateBickerDialog.setOnDateSetListener { p0, year, month, day ->
            calendar.set(Calendar.DAY_OF_MONTH, day)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.YEAR, year)
            viewBinding.date.setText(calendar.formatDate())
        }
        dateBickerDialog.show()
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
        if (viewBinding.time.text.isNullOrBlank()) {
            viewBinding.timeContainer.error = "Time is require"
            isValid = false
        } else {
            viewBinding.timeContainer.error = null
        }
        return isValid
    }

    private fun insertTask() {
        val task = Task(
            title = viewBinding.title.text.toString(),
            description = viewBinding.description.text.toString(),
            time = calendar.getTimeOnly() ,
            date = calendar.getDateOnly()
        )
        viewModel.addTask(task)
    }

    var onTaskAddedListener: OnTaskAddedListener? = null

    interface OnTaskAddedListener {
        fun onTaskAdded()
    }
}