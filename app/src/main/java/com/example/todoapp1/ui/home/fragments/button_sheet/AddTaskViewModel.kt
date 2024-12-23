package com.example.todoapp1.ui.home.fragments.button_sheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Task
import com.example.domain.usecase.TasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val tasksUseCase: TasksUseCase
) : ViewModel() {

    fun addTask(task: Task) {
        tasksUseCase.insertTask(task)
    }
}