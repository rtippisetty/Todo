package com.ranga.todo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ranga.todo.data.model.TodoItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_items")
    fun getAll(): Flow<List<TodoItemEntity>>

    @Insert
    suspend fun add(item: TodoItemEntity)
}