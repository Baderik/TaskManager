package com.example.taskmanager.Domain.UseCases

import com.example.taskmanager.Data.Enity.Task
import com.example.taskmanager.Domain.Repository.Repository

class DeleteTaskUseCase(private val repository: Repository) {
    suspend fun execute(task:Task){
        repository.removeTask(task)
    }
}