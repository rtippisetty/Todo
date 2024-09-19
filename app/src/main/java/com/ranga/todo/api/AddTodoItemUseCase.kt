package com.ranga.todo.api

import com.ranga.todo.api.model.Todo

fun interface AddTodoItemUseCase {
    suspend fun add(todo: Todo)
}