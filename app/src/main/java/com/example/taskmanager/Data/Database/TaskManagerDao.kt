package com.example.taskmanager.Data.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskmanager.Data.Enity.Task
import java.util.UUID

@Dao
interface TaskManagerDao {

    @Insert
    suspend fun addTask(task:Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM Task WHERE mainTaskId IS NULL AND isSelected = 0")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE id=(:id)")
    fun getTask(id:UUID): LiveData<Task>

    @Query("SELECT * FROM Task WHERE mainTaskId=(:mainTaskId)")
    fun getSubtask(mainTaskId:UUID): LiveData<Task?>

    @Query("SELECT * FROM Task WHERE isSuccess = 1 AND mainTaskId IS NULL")
    fun getSuccessTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE isSelected = 1 AND mainTaskId IS NULL")
    fun getSelectedTasks(): LiveData<List<Task>>

    @Query("UPDATE Task SET isSuccess = 1 WHERE mainTaskId=(:mainTaskId)")
    fun noteSubtasksAsCompiled(mainTaskId: UUID)

    @Query("DELETE FROM Task WHERE mainTaskId=(:mainTaskId)")
    fun deleteSubtask(mainTaskId: UUID)
}