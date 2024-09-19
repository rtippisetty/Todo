package com.ranga.todo.domain

import com.ranga.todo.api.AddTodoItemUseCase
import com.ranga.todo.api.model.Todo
import javax.inject.Inject

class AddTodoItemUseCaseImpl @Inject constructor(private val todoRepository: TodoRepository) :
    AddTodoItemUseCase {

    override suspend fun add(todo: Todo) {
        todoRepository.addItem(todo)
    }
}