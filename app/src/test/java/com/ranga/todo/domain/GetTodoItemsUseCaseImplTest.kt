package com.ranga.todo.domain

import com.ranga.todo.api.GetTodoItemsUseCase
import com.ranga.todo.api.model.Todo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows

class GetTodoItemsUseCaseImplTest {

    private lateinit var getTodoItemsUseCase: GetTodoItemsUseCase
    private val todoRepository: TodoRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        getTodoItemsUseCase = GetTodoItemsUseCaseImpl(todoRepository)
    }

    @Test
    fun `Given todoRepository returns Todo list, When items invoked Then it should returns the same list`() =
        runTest {
            // Given
            val expectedTodos = listOf(Todo("Task 1"), Todo("Task 2"))
            coEvery { todoRepository.getItems() } returns flowOf(expectedTodos)

            // When
            val result = getTodoItemsUseCase.items().toList()

            // Then
            assertEquals(expectedTodos, result.first())
        }

    @Test
    fun `Given todoRepository fails When items invoked Then it should throw exception`() = runTest {
        coEvery { todoRepository.getItems() } throws Exception("Database error")

        assertThrows<Exception> {
            getTodoItemsUseCase.items()
        }
    }
}