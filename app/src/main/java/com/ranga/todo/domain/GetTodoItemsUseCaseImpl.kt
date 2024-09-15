package com.ranga.todo.domain

import com.ranga.todo.api.GetTodoItemsUseCase
import com.ranga.todo.api.model.Todo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoItemsUseCaseImpl @Inject constructor(private val todoRepository: TodoRepository) :
    GetTodoItemsUseCase {
    override fun items(): Flow<List<Todo>> {
        return todoRepository.getItems()
    }
}