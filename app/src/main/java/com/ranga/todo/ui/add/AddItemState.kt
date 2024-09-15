package com.ranga.todo.ui.add

sealed class AddItemState {
    data object Success : AddItemState()
    data object Error : AddItemState()
    data object InProgress : AddItemState()
    data object None : AddItemState()
}
