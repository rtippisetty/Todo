package com.ranga.todo.data

import com.ranga.todo.api.model.Todo
import com.ranga.todo.data.local.TodoDao
import com.ranga.todo.data.model.TodoItemEntity
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
            val todoEntities =
                listOf(TodoItemEntity("1", "Task 1", false), TodoItemEntity("2", "Task 2", false))
            val expectedTodos = listOf(Todo("1", "Task 1", false), Todo("2", "Task 2", false))

            // Mock the behavior of todoDao.getAll()
            coEvery { todoDao.getAll() } returns flowOf(todoEntities)

            // When
            val result = todoRepository.getItems().toList()

            // Then
            assertEquals(expectedTodos, result.first())
        }

    @Test
    fun `Given todoDao returns empty list, When getItems invoked Then it should return empty list`() = runTest {
        // Given
        coEvery { todoDao.getAll() } returns flowOf(emptyList())
        // When
        val result = todoRepository.getItems().toList()
        // Then
        assertEquals(emptyList<Todo>(), result.first())
    }

    @Test
    fun `Given todoDao fails When getItems invoked Then it should throw exception`() = runTest {
        coEvery { todoDao.getAll() } throws Exception("Database error")

        assertThrows<Exception> {
            todoRepository.getItems()
        }
    }

    @Test
    fun `Given todoDao add TodoItem, When addItem invoked Then it should call todoDao add`() =
        runTest {
            coEvery { todoDao.add(any()) } returns Unit

            todoRepository.addItem(Todo("1", "Task 1", false))

            coVerify { todoDao.add(any()) }
        }

    @Test
    fun `Given todoDao fails When saveItem invoked Then it should throw exception`() = runTest {
        coEvery { todoDao.add(any()) } throws Exception("Database error")

        assertThrows<Exception> {
            todoRepository.addItem(Todo("1", "Task 1", false))
        }
    }
}