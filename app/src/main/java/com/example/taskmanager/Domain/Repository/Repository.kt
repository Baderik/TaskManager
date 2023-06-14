package com.example.taskmanager.Domain.Repository

import androidx.lifecycle.LiveData
import com.example.taskmanager.Data.Enity.Task
import java.util.UUID

interface Repository {
    fun getAllTasks(): LiveData<List<Task>>

    fun getTask(id: UUID)

    fun getSelectedTasks():LiveData<List<Task>>

    fun getSuccessTasks():LiveData<List<Task>>

    fun getSubtasks(mainTaskId: UUID):LiveData<Task?>

    suspend fun removeTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun add(task: Task)
}