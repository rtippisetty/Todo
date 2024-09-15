package com.ranga.todo.domain

import com.ranga.todo.api.SaveTodoItemUseCase
import com.ranga.todo.api.model.Todo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class SaveTodoItemUseCaseImplTest {
    private lateinit var saveTodoItemUseCase: SaveTodoItemUseCase
    private val todoRepository: TodoRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        saveTodoItemUseCase = SaveTodoItemUseCaseImpl(todoRepository)
    }

    @Test
    fun `Given todoRepository saves TodoItem, When saveItem invoked Then it should call todoRepository saveItem`() =
        runTest {
            // Given
            val todo = Todo("Task 1")
            coEvery { todoRepository.saveItem(todo) } returns Unit
            // When
            saveTodoItemUseCase.save(todo)
            // Then
            coVerify { todoRepository.saveItem(todo) }
        }

    @Test
    fun `Given todoRepository fails When saveItem invoked Then it should throw exception`() =
        runTest {
            coEvery { todoRepository.saveItem(any()) } throws Exception("Database error")

            assertThrows<Exception> {
                saveTodoItemUseCase.save(Todo("Task 1"))
            }
        }
}