package com.example.taskmanager.presentation.ListFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Delete
import com.example.taskmanager.Data.Enity.Task
import com.example.taskmanager.Data.Repository.DatabaseRepository
import com.example.taskmanager.Domain.UseCases.DeleteTaskUseCase
import com.example.taskmanager.Domain.UseCases.GetSelectedTasksUseCase
import com.example.taskmanager.Domain.UseCases.GetSuccessTasksUseCase
import com.example.taskmanager.Domain.UseCases.GetTasksUseCase
import com.example.taskmanager.Domain.UseCases.UpdateSelectedTaskUseCase
import com.example.taskmanager.Domain.UseCases.UpdateTaskDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListFragmentViewModel:ViewModel() {
    private val repository = DatabaseRepository.get()

    private val getTask = GetTasksUseCase(repository)
    private val getSelectedTask = GetSelectedTasksUseCase(repository)
    private val getSuccessTasks = GetSuccessTasksUseCase(repository)
    private val updateSelectedTask = UpdateSelectedTaskUseCase(repository)
    private val updateSuccessTask = UpdateTaskDataUseCase(repository)
    private val deleteTask = DeleteTaskUseCase(repository)

    val tasks: LiveData<List<Task>> = getTask.execute()
    val selectedTasks: LiveData<List<Task>> = getSelectedTask.execute()
    val successTasks: LiveData<List<Task>> = getSuccessTasks.execute()

    fun isSelectedButton(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSelectedTask.execute(task)
        }
    }

    fun isSuccessButton(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            updateSuccessTask.execute(task)
        }
    }

    fun isDeleteButton(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTask.execute(task)
        }
    }
}