package com.ranga.todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ranga.todo.data.model.TodoItemEntity

@Database(entities = [TodoItemEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}