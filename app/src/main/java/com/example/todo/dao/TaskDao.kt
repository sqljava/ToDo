package com.example.todo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.DeleteColumn
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todo.entities.Task


@Dao
interface TaskDao {

    @Insert
    fun addTask(task: Task)

    @Query("SELECT * FROM tasks")
    fun showAllTasks():MutableList<Task>

    @Query("SELECT * FROM tasks WHERE ID= :id")
    fun showTask(id: Int?):Task

//    @Delete
//    fun removeTask(id: Int?)

    @Update
    fun updateTask(task: Task)




}