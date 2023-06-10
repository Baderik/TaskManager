package com.example.taskmanager.Data.Enity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Task(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val description: String,
    val isSuccess: Boolean,
    val isSelected: Boolean,
    val mainTaskId: UUID?
)
