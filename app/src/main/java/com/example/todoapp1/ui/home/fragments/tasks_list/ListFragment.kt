package com.example.todoapp1.ui.home.fragments.tasks_list

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.domain.model.Task
import com.example.todoapp1.ui.datails.DetailsActivity
import com.example.todoapp1.databinding.FragmentListBinding
import com.example.todoapp1.ui.formatDate
import com.example.todoapp1.ui.getDateOnly
import com.prolificinteractive.materialcalendarview.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var viewBinding: FragmentListBinding
    private lateinit var viewModel: ListFragmentViewModel
    private var taskAdapter = TasksAdapter(null)
    private var selectedDate = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[ListFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentListBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onStart() {
        super.onStart()
        loadTasks()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        viewBinding.recyclerView.adapter = taskAdapter
        taskAdapter.onItemDeletedListener = object : TasksAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, task: Task) {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setMessage("Do You Want To Delete This Task ?")
                    .setPositiveButton(
                        "Yes"
                    ) { p0, p1 ->
                        viewModel.deleteTask(task)
                        taskAdapter.updateTaskDeleted(task, position)
                    }
                    .setNegativeButton(
                        "No"
                    ) { p0, p1 -> p0?.dismiss() }
                    .show()
            }
        }
        taskAdapter.onItemClickListener = object : TasksAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, task: Task) {
                val intent = Intent(requireActivity(), DetailsActivity::class.java)
                intent.putExtra("task", task)
                startActivity(intent)
            }
        }
        taskAdapter.onTodoStateChangedListener = object : TasksAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, task: Task) {
                viewModel.updateTask(task)
            }
        }
        viewBinding.calendarView.selectedDate = CalendarDay.today()
        viewBinding.calendarView.setOnDateChangedListener { widget, date, selected ->
            if (selected) {
                changeSelectedDate(date)
                loadTasks()
            }
        }
    }

    private fun changeSelectedDate(date: CalendarDay) {
        selectedDate.set(Calendar.DAY_OF_MONTH, date.day)
        selectedDate.set(Calendar.MONTH, date.month - 1)
        selectedDate.set(Calendar.YEAR, date.year)
        selectedDate.formatDate()
    }

    fun loadTasks() {
        val tasks = viewModel.getTasksByDate(selectedDate.getDateOnly())
        taskAdapter.bindTasks(tasks)
    }
}