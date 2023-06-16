package com.example.taskmanager.presentation.DetailFragment

import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.taskmanager.Data.Enity.Task
import com.example.taskmanager.Data.Repository.DatabaseRepository
import com.example.taskmanager.Domain.UseCases.DeleteTaskUseCase
import com.example.taskmanager.Domain.UseCases.GetDefiniteTaskUseCase
import com.example.taskmanager.Domain.UseCases.GetSelectedTasksUseCase
import com.example.taskmanager.Domain.UseCases.GetSuccessTasksUseCase
import com.example.taskmanager.Domain.UseCases.GetTasksUseCase
import com.example.taskmanager.Domain.UseCases.UpdateSelectedTaskUseCase
import com.example.taskmanager.Domain.UseCases.UpdateTaskDataUseCase
import java.util.UUID

class DetailFragmentViewModel:ViewModel() {
    private val repository = DatabaseRepository.get()

    private val getDefiniteTask = GetDefiniteTaskUseCase(repository)

    private val taskId = MutableLiveData<UUID>()
    val task = Transformations.switchMap(taskId){it->
        getDefiniteTask.execute(it)
    }

    fun onCreate(id:UUID) { taskId.value = id }
}