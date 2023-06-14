package com.example.taskmanager.Data.Enity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Task(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    var description: String,
    var isSuccess: Boolean,
    var isSelected: Boolean,
    var mainTaskId: UUID?
)
