package com.ranga.todo.domain

import com.ranga.todo.api.AddTodoItemUseCase
import com.ranga.todo.api.model.Todo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class AddTodoItemUseCaseImplTest {
    private lateinit var useCase: AddTodoItemUseCase
    private val todoRepository: TodoRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        useCase = AddTodoItemUseCaseImpl(todoRepository)
    }

    @Test
    fun `Given todoRepository adds TodoItem, When addItem invoked Then it should call todoRepository addItem`() =
        runTest {
            // Given
            val todo = Todo("1","Task 1", false)
            coEvery { todoRepository.addItem(todo) } returns Unit
            // When
            useCase.add(todo)
            // Then
            coVerify { todoRepository.addItem(todo) }
        }

    @Test
    fun `Given todoRepository fails When saveItem invoked Then it should throw exception`() =
        runTest {
            coEvery { todoRepository.addItem(any()) } throws Exception("Database error")

            assertThrows<Exception> {
                useCase.add(Todo("2","Task 2", false))
            }
        }
}