package com.ranga.todo.ui.add

import com.ranga.todo.api.SaveTodoItemUseCase
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
    private val saveItemUseCase: SaveTodoItemUseCase = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        viewModel = AddItemViewModel(saveItemUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given a valid title When saveItem invoked Then it should call saveItemUseCase save`() = runTest {
        val title = "Task 1"
        viewModel.saveItem(title)

        advanceUntilIdle()

        coVerify { saveItemUseCase.save(Todo(title = title)) }
        assertEquals(AddItemState.Success, viewModel.saveItemState.replayCache.first())
    }

    @Test
    fun `Given an invalid title When saveItem invoked Then it should not call saveItemUseCase save`() = runTest {
        val title = "Error Task"
        viewModel.saveItem(title)

        advanceUntilIdle()

        coVerify(exactly = 0) { saveItemUseCase.save(any()) }
        assertEquals(AddItemState.Error, viewModel.saveItemState.replayCache.first())
    }

}