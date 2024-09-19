package com.ranga.todo.ui.add

import com.ranga.todo.api.AddTodoItemUseCase
import com.ranga.todo.api.model.Todo
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class AddItemViewModelTest {
    private lateinit var viewModel: AddItemViewModel
    private val useCase: AddTodoItemUseCase = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        viewModel = AddItemViewModel(useCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given a valid title When addItem invoked Then it should call addItemUseCase`() = runTest {
        // Given
        val title = "Task 1"
        // When
        viewModel.addItem(title)

        advanceUntilIdle()
        // Then
        coVerify { useCase.add(any()) }
        assertEquals(AddItemState.Success, viewModel.saveItemState.replayCache.first())
    }

    @Test
    fun `Given an invalid title When saveItem invoked Then it should not call saveItemUseCase save`() = runTest {
        val title = "Error Task"
        viewModel.addItem(title)

        advanceUntilIdle()

        coVerify(exactly = 0) { useCase.add(any()) }
        assertEquals(AddItemState.Error, viewModel.saveItemState.replayCache.first())
    }

}