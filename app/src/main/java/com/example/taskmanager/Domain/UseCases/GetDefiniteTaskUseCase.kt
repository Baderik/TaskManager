package com.example.taskmanager.Domain.UseCases

import androidx.lifecycle.LiveData
import com.example.taskmanager.Data.Enity.Task
import com.example.taskmanager.Domain.Repository.Repository
import java.util.UUID

class GetDefiniteTaskUseCase(private val repository: Repository) {
    fun execute(id:UUID):LiveData<Task>{
        return repository.getTask(id)
    }
}