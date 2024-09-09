package com.example.todoapp1.ui.home.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todoapp1.DataBase.Task
import com.example.todoapp1.DataBase.ToDoDataBase
import com.example.todoapp1.DetailsActivity
import com.example.todoapp1.databinding.FragmentListBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar

class ListFragment : Fragment() {
    lateinit var viewBinding: FragmentListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentListBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onStart() {
        super.onStart()
        loadTasks()
    }

    fun loadTasks() {
        context?.let {
            val tasks = ToDoDataBase.getInstance(it)
                .getDao().getAllTasksByDate(selectedDate.timeInMillis)
            taskAdapter.bindTasks(tasks)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    var taskAdapter = TasksAdapter(null)
    var selectedDate = Calendar.getInstance()

    init {
        selectedDate.set(Calendar.HOUR_OF_DAY, 0)
        selectedDate.set(Calendar.MINUTE, 0)
        selectedDate.set(Calendar.SECOND, 0)
        selectedDate.set(Calendar.MILLISECOND, 0)
    }

    private fun initViews() {
        viewBinding.recyclerView.adapter = taskAdapter
        taskAdapter.onItemClickListener = object : TasksAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, task: Task) {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setMessage("Do You Want To Delete This Task ?")
                    .setPositiveButton(
                        "Yes"
                    ) { p0, p1 ->
                        deleteTaskFromDataBase(task)
                        taskAdapter.updateTaskDeleted(task)
                    }
                    .setNegativeButton(
                        "No"
                    ) { p0, p1 -> p0?.dismiss() }
                    .show()
            }
        }
        taskAdapter.onItemClickListener2 = object : TasksAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, task: Task) {
                val intent = Intent(requireActivity(), DetailsActivity::class.java)
                intent.putExtra("task", task)
                startActivity(intent)
            }
        }
        taskAdapter.onTodoStateChangedListener = object : TasksAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, task: Task) {
                ToDoDataBase.getInstance(requireContext()).getDao()
                    .updateTask(task)
            }
        }
        viewBinding.calendarView.selectedDate = CalendarDay.today()
        viewBinding.calendarView.setOnDateChangedListener { widget, date, selected ->
            if (selected) {
                selectedDate.set(Calendar.DAY_OF_MONTH, date.day)
                selectedDate.set(Calendar.MONTH, date.month - 1)
                selectedDate.set(Calendar.YEAR, date.year)
                loadTasks()
            }
        }
    }

    private fun deleteTaskFromDataBase(task: Task) {
        ToDoDataBase.getInstance(requireContext())
            .getDao()
            .deleteTask(task)
    }


}