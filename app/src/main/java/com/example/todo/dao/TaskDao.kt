package com.example.todo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todo.entities.Task


@Dao
interface TaskDao {

    @Insert
    fun addTask(task: Task)

    @Query("SELECT * FROM tasks")
    fun showAllTasks():MutableList<Task>

    @Query("SELECT * FROM tasks WHERE ID= :id")
    fun showTask(id: Int):Task




}