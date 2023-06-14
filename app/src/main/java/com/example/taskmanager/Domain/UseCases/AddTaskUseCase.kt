package com.example.taskmanager.Domain.UseCases

import com.example.taskmanager.Data.Enity.Task
import com.example.taskmanager.Domain.Repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTaskUseCase(private val repository: Repository) {
    suspend fun execute(task: Task){
        repository.addTask(task)
    }
}