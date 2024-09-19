package com.ranga.todo.data

import com.ranga.todo.api.model.Todo
import com.ranga.todo.data.local.TodoDao
import com.ranga.todo.data.model.toDomain
import com.ranga.todo.data.model.toEntity
import com.ranga.todo.domain.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor (private val todoDao: TodoDao) : TodoRepository {

    override fun getItems(): Flow<List<Todo>> {
        return todoDao.getAll().map { items ->
            items.map { it.toDomain() }
        }
    }

    override suspend fun addItem(todo: Todo) {
        todoDao.add(todo.toEntity())
    }
}