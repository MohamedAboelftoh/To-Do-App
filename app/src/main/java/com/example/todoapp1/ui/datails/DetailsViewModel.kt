package com.example.todoapp1.ui.datails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Task
import com.example.domain.usecase.TasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val tasksUseCase: TasksUseCase
):ViewModel() {
    fun updateTask(task: Task){
            tasksUseCase.updateTask(task)
    }
}