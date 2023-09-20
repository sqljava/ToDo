package com.example.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.dao.TaskDao
import com.example.todo.entities.Task
import kotlinx.coroutines.internal.synchronized



@Database(entities = [Task::class], version = 3)
abstract class AppDataBase():RoomDatabase(){

    abstract fun getTaskDao(): TaskDao

    companion object{

        @Volatile
        private var instance: AppDataBase? = null

        fun getDataBsae(context:Context):AppDataBase{

            if (instance == null){
                instance = Room.databaseBuilder(context,
                    AppDataBase::class.java, "app_db")
                    .allowMainThreadQueries().build()
            }
            return instance!!
        }
    }



}