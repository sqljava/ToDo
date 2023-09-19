package com.example.todo.entities

import android.text.Editable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var text: String,
    var filePath:String
)