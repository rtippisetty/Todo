package com.ranga.todo.data

import com.ranga.todo.api.model.Todo
import com.ranga.todo.data.local.TodoDao
import com.ranga.todo.data.local.TodoItem
import com.ranga.todo.domain.TodoRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows

class TodoRepositoryImplTest {

    private val todoDao: TodoDao = mockk(relaxed = true)
    private lateinit var todoRepository: TodoRepository

    @Before
    fun setUp() {
        todoRepository = TodoRepositoryImpl(todoDao)
    }

    @Test
    fun `Given todoDao returns TodoItem list, When getItems invoked Then it should map TodoItem list to Todo list`() =
        runTest {
            // Given
            val todoEntities = listOf(TodoItem(1, "Task 1"), TodoItem(2, "Task 2"))
            val expectedTodos = listOf(Todo("Task 1"), Todo("Task 2"))

            // Mock the behavior of todoDao.getAll()
            coEvery { todoDao.getAll() } returns flowOf(todoEntities)

            // When
            val result = todoRepository.getItems().toList()

            // Then
            assertEquals(expectedTodos, result.first())
        }
    @Test
    fun `Given todoDao fails When getItems invoked Then it should throw exception`() = runTest {
        coEvery { todoDao.getAll() } throws Exception("Database error")

        assertThrows<Exception> {
            todoRepository.getItems()
        }
    }

    @Test
    fun `Given todoDao saves TodoItem, When saveItem invoked Then it should call todoDao insert`() = runTest {
        coEvery { todoDao.insert(any()) } returns Unit

        todoRepository.saveItem(Todo("Task 1"))

        coVerify { todoDao.insert(any()) }
    }

    @Test
    fun `Given todoDao fails When saveItem invoked Then it should throw exception`() = runTest {
        coEvery { todoDao.insert(any()) } throws Exception("Database error")

        assertThrows<Exception> {
            todoRepository.saveItem(Todo("Task 1"))
        }
    }
}