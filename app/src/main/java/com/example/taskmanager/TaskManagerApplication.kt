package com.example.taskmanager

import android.app.Application
import com.example.taskmanager.Data.Repository.DatabaseRepository

class TaskManagerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseRepository.init(this)
    }
}