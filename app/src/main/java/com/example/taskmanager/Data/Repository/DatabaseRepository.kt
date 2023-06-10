package com.example.taskmanager.Data.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.taskmanager.Data.Database.TaskManagerDatabase
import com.example.taskmanager.Data.Enity.Task
import java.lang.IllegalStateException
import java.util.UUID

private const val DATABASE_NAME = "task-database"

class DatabaseRepository private constructor(context: Context){

    private val database: TaskManagerDatabase = Room.databaseBuilder(
        context.applicationContext,
        TaskManagerDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val taskDao = database.taskManagerDao()

    fun getAllTasks(): LiveData<List<Task>> = taskDao.getAllTasks()

    fun getTask(id: UUID) = taskDao.getTask(id = id)

    fun getSelectedTasks():LiveData<List<Task>> = taskDao.getSelectedTasks()

    fun getSuccessTasks():LiveData<List<Task>> = taskDao.getSuccessTasks()

    fun getSubtasks(mainTaskId: UUID):LiveData<Task?> = taskDao.getSubtask(mainTaskId = mainTaskId)

    suspend fun removeTask(task: Task){
        taskDao.deleteSubtask(task.id)
        taskDao.deleteTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task = task)
        if (task.isSuccess)
            taskDao.noteSubtasksAsCompiled(task.id)
    }

    suspend fun add(task: Task){
        taskDao.addTask(task)
    }


    companion object{
        private var Instance: DatabaseRepository? = null

        fun init(context: Context){
            if (Instance == null){
                Instance = DatabaseRepository(context = context)
            }
        }

        fun get(): DatabaseRepository{
            return Instance ?: throw IllegalStateException("DatabaseRepository must be initialize")
        }
    }
}