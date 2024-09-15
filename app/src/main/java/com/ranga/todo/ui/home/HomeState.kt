package com.ranga.todo.ui.home

import com.ranga.todo.api.model.Todo

sealed class HomeState {
    data class Success(val items: List<Todo>) : HomeState()
    data class Error(val message: String) : HomeState()
    data object Loading : HomeState()
}