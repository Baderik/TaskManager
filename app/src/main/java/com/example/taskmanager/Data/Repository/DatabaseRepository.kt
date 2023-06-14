package com.example.taskmanager.Data.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.taskmanager.Data.Database.TaskManagerDatabase
import com.example.taskmanager.Data.Enity.Task
import com.example.taskmanager.Domain.Repository.Repository
import java.lang.IllegalStateException
import java.util.UUID

private const val DATABASE_NAME = "task-database"

class DatabaseRepository private constructor(context: Context):Repository{

    private val database: TaskManagerDatabase = Room.databaseBuilder(
        context.applicationContext,
        TaskManagerDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val taskDao = database.taskManagerDao()

    override fun getAllTasks(): LiveData<List<Task>> = taskDao.getAllTasks()

    override fun getTask(id: UUID) = taskDao.getTask(id = id)

    override fun getSelectedTasks():LiveData<List<Task>> = taskDao.getSelectedTasks()

    override fun getSuccessTasks():LiveData<List<Task>> = taskDao.getSuccessTasks()

    override fun getSubtasks(mainTaskId: UUID):LiveData<Task?> = taskDao.getSubtask(mainTaskId = mainTaskId)

    override suspend fun removeTask(task: Task){
        taskDao.deleteSubtask(task.id)
        taskDao.deleteTask(task)
    }

    override suspend fun updateTask(task: Task){
        taskDao.updateTask(task = task)
        if (task.isSuccess)
            taskDao.noteSubtasksAsCompiled(task.id)
    }

    override suspend fun add(task: Task){
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