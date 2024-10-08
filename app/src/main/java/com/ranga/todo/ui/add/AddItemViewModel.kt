package com.ranga.todo.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranga.todo.api.AddTodoItemUseCase
import com.ranga.todo.api.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val createItemUseCase: AddTodoItemUseCase
) : ViewModel() {
    companion object {
        private const val SAVE_DELAY = 3000L
    }
    private val _saveItemState = MutableSharedFlow<AddItemState>(replay = 1)
    val saveItemState: SharedFlow<AddItemState> = _saveItemState

    fun addItem(title: String) {
        viewModelScope.launch {
            _saveItemState.emit(AddItemState.InProgress)
            try {
                if (title.contains("error", ignoreCase = true)) {
                    throw RuntimeException("Failed to add TODO")
                }
                delay(SAVE_DELAY)
                createItemUseCase.add(
                    Todo(
                        id = UUID.randomUUID().toString(),
                        title = title,
                        isCompleted = false
                    )
                )
                _saveItemState.emit(AddItemState.Success)
            } catch (e: Exception) {
                _saveItemState.emit(AddItemState.Error)
            }
        }
    }
}
