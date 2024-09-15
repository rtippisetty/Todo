package com.ranga.todo.data

import com.ranga.todo.api.model.Todo
import com.ranga.todo.data.local.TodoDao
import com.ranga.todo.data.local.TodoItem
import com.ranga.todo.domain.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor (private val todoDao: TodoDao) : TodoRepository {

    override fun getItems(): Flow<List<Todo>> {
        return todoDao.getAll().map { items ->
            items.map { item -> Todo(item.title) }
        }
    }

    override suspend fun saveItem(todo: Todo) {
        todoDao.insert(TodoItem(title = todo.title))
    }
}