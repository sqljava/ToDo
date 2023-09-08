package com.example.todo.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var text: String
)