package com.example.taskmanager.Data.Repository

import android.content.Context
import androidx.room.Room
import com.example.taskmanager.Data.Database.TaskManagerDatabase
import java.lang.IllegalStateException

private const val DATABASE_NAME = "task-database"

class DatabaseRepository private constructor(context: Context){

    private val database: TaskManagerDatabase = Room.databaseBuilder(
        context.applicationContext,
        TaskManagerDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val taskDao = database.taskManagerDao()






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