package com.ranga.todo.domain

import com.ranga.todo.api.SaveTodoItemUseCase
import com.ranga.todo.api.model.Todo
import javax.inject.Inject

class SaveTodoItemUseCaseImpl @Inject constructor(private val todoRepository: TodoRepository) :
    SaveTodoItemUseCase {

    override suspend fun save(todo: Todo) {
        todoRepository.saveItem(todo)
    }
}