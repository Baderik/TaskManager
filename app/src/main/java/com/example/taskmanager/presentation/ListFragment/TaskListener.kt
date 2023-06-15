package com.example.taskmanager.presentation.ListFragment

import com.example.taskmanager.Data.Enity.Task
import java.util.UUID

interface TaskListener {
    fun onClick(taskId: UUID)

    fun onSelectedPress(task: Task)

    fun onSuccessPress(task: Task)

    fun onDeletePress(task: Task)
}