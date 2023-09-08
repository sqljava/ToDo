package com.example.todo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todo.entities.Task


@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM task")
    fun showAllTasks()

    @Query("SELECT * FROM task WHERE ID= :id")
    fun showTask(id: Int)




}