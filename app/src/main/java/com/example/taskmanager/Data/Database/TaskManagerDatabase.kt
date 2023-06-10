package com.example.taskmanager.Data.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskmanager.Data.Enity.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskManagerDatabase: RoomDatabase() {
    abstract fun taskManagerDao(): TaskManagerDao
}