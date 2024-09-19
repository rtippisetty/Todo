package com.ranga.todo.ui.home

import com.ranga.todo.api.GetTodoItemsUseCase
import com.ranga.todo.api.model.Todo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel
    private val getTodoItemsUseCase: GetTodoItemsUseCase = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given getTodoItemsUseCase returns Todo list When viewModel is initialized Then homeState should be Success`() =
        runTest {
            // Given
            val expectedTodos = listOf(Todo("1","Task 1", false), Todo("2","Task 2", false))
            coEvery { getTodoItemsUseCase.items() } returns flowOf(
                expectedTodos
            )

            // When
            viewModel = HomeViewModel(getTodoItemsUseCase)

            // Then
            assertEquals(HomeState.Loading, viewModel.uiState.value)
            advanceUntilIdle()
            assertEquals(
                expectedTodos,
                (viewModel.uiState.value as HomeState.Success).items
            )
        }

    @Test
    fun `Given getTodoItemsUseCase fails When viewModel is initialized Then homeState should be Error`() =
        runTest {
            // Given
            coEvery { getTodoItemsUseCase.items() } throws Exception("Database error")

            // When
            viewModel = HomeViewModel(getTodoItemsUseCase)

            // Then
            assertEquals(HomeState.Loading, viewModel.uiState.value)
            advanceUntilIdle()
            assertEquals(
                "Database error",
                (viewModel.uiState.value as HomeState.Error).message
            )
        }

    @Test
    fun `Given getTodoItemsUseCase returns Todo list When searchQuery is updated Then homeState should be have filtered list`() =
        runTest {
            // Given
            val expectedTodos = listOf(Todo("1","Task 1", false), Todo("2","Task 2", false))
            coEvery { getTodoItemsUseCase.items() } returns flowOf(
                expectedTodos
            )
            viewModel = HomeViewModel(getTodoItemsUseCase)
            advanceUntilIdle()
            // When
            viewModel.onSearch("Task 1")
            // Then
            advanceUntilIdle()
            assertEquals(
                expectedTodos.first(),
                (viewModel.uiState.value as HomeState.Success).items.first()
            )
        }

    @Test
    fun `Given getTodoItemsUseCase returns Todo list When searchQuery is updated And getTodoItemsUseCase fails Then homeState should show error`() =
        runTest {
            // Given
            val expectedTodos = listOf(Todo("1","Task 1", false), Todo("2","Task 2", false))
            coEvery { getTodoItemsUseCase.items() } returns flowOf(
                expectedTodos
            )
            viewModel = HomeViewModel(getTodoItemsUseCase)
            advanceUntilIdle()

            coEvery { getTodoItemsUseCase.items() } throws Exception("Database error")

            // When
            viewModel.onSearch("Task 1")
            // Then
            advanceUntilIdle()
            assertEquals(
                "Database error",
                (viewModel.uiState.value as HomeState.Error).message
            )
        }
}