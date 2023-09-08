package com.example.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.todo.dao.TaskDao
import com.example.todo.entities.Task
import kotlinx.coroutines.internal.synchronized



@Database(entities = [Task::class], version = 1)
abstract class AppDataBase {

    abstract fun getTaskDao(): TaskDao

    companion object{

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDataBsae(context:Context):AppDataBase{
            var tempInstance = INSTANCE
            if (tempInstance == null){
                tempInstance = Room.databaseBuilder(context, AppDataBase::class.java, "app_db").allowMainThreadQueries().build()




            }


        }
    }



}