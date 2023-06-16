package com.example.taskmanager.presentation.DetailFragment

import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.Data.Enity.Task
import com.example.taskmanager.Data.Repository.DatabaseRepository
import com.example.taskmanager.Domain.UseCases.AddTaskUseCase
import com.example.taskmanager.Domain.UseCases.DeleteTaskUseCase
import com.example.taskmanager.Domain.UseCases.GetDefiniteSubtasksUseCase
import com.example.taskmanager.Domain.UseCases.GetDefiniteTaskUseCase
import com.example.taskmanager.Domain.UseCases.GetSelectedTasksUseCase
import com.example.taskmanager.Domain.UseCases.GetSuccessTasksUseCase
import com.example.taskmanager.Domain.UseCases.GetTasksUseCase
import com.example.taskmanager.Domain.UseCases.UpdateSelectedTaskUseCase
import com.example.taskmanager.Domain.UseCases.UpdateTaskDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class DetailFragmentViewModel:ViewModel() {
    private val repository = DatabaseRepository.get()

    private val getDefiniteTask = GetDefiniteTaskUseCase(repository)
    private val addSubtask = AddTaskUseCase(repository)
    private val getSubtasks = GetDefiniteSubtasksUseCase(repository)
    private val deleteTask = DeleteTaskUseCase(repository)
    private val updateSuccessTask = UpdateTaskDataUseCase(repository)


    private val taskId = MutableLiveData<UUID>()
    val task: LiveData<Task> = Transformations.switchMap(taskId){ it->
        getDefiniteTask.execute(it)
    }

    val subtasks: LiveData<List<Task?>> = Transformations.switchMap(taskId){
        getSubtasks.execute(it)
    }

    fun onCreate(id:UUID) { taskId.value = id }

    fun addSubtaskInDatabase(task: Task){
        viewModelScope.launch {
            addSubtask.execute(task)
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