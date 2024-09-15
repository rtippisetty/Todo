package com.ranga.todo.api

import com.ranga.todo.api.model.Todo
import kotlinx.coroutines.flow.Flow

fun interface GetTodoItemsUseCase {
    fun items(): Flow<List<Todo>>
}