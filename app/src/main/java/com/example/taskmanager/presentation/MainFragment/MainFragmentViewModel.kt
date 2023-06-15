package com.example.taskmanager.presentation.MainFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.Data.Enity.Task
import com.example.taskmanager.Data.Repository.DatabaseRepository
import com.example.taskmanager.Domain.UseCases.AddTaskUseCase
import kotlinx.coroutines.launch

class MainFragmentViewModel:ViewModel() {
    private val addTask = AddTaskUseCase(DatabaseRepository.get())

    fun addTaskInDatabase(task: Task){
        viewModelScope.launch {
            addTask.execute(task)
        }
    }
}