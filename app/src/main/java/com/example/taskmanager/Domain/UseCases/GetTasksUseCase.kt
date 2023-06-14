package com.example.taskmanager.Domain.UseCases

import androidx.lifecycle.LiveData
import com.example.taskmanager.Data.Enity.Task
import com.example.taskmanager.Domain.Repository.Repository

class GetTasksUseCase(private val repository: Repository) {
    fun execute(): LiveData<List<Task>>{
        return repository.getAllTasks()
    }
}