package com.ranga.todo.api

import com.ranga.todo.api.model.Todo

fun interface SaveTodoItemUseCase {
    suspend fun save(todo: Todo)
}