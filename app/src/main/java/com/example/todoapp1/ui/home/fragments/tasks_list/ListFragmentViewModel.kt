package com.example.todoapp1.ui.home.fragments.tasks_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Task
import com.example.domain.usecase.TasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListFragmentViewModel @Inject constructor(
    private val tasksUseCase: TasksUseCase
) : ViewModel() {
    fun deleteTask(task: Task) {
        tasksUseCase.deleteTask(task)
    }

    fun updateTask(task: Task) {
        tasksUseCase.updateTask(task)
    }

    fun getTasksByDate(date: Long): List<Task> {
        return tasksUseCase.getAllTasksByDate(date)
    }

}
