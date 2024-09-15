package com.ranga.todo.domain

import com.ranga.todo.api.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getItems(): Flow<List<Todo>>
    suspend fun saveItem(todo: Todo)
}
