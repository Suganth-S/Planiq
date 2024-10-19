package com.example.todoappjetpackcompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoappjetpackcompose.data.dao.ToDoDao
import com.example.todoappjetpackcompose.data.models.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}