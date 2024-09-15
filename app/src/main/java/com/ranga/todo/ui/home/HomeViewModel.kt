package com.ranga.todo.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranga.todo.api.GetTodoItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(private val getTodoItemsUseCase: GetTodoItemsUseCase) :
    ViewModel() {
    companion object {
        private const val SEARCH_DELAY = 2000L
        private const val TAG = "HomeViewModel"
    }

    private val _uiState = MutableStateFlow<HomeState>(HomeState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private var filterJob: Job? = null

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            try {
                _uiState.value = HomeState.Loading
                getTodoItemsUseCase.items()
                    .collect { _uiState.value = HomeState.Success(it) }
            } catch (e: Exception) {
                _uiState.value = HomeState.Error(e.message ?: "Something went wrong")
            }
        }
    }

    fun onSearch(query: String) {
        _searchQuery.value = query
        applyFiltering()
    }

    private fun applyFiltering() {
        filterJob?.cancel()

        filterJob = viewModelScope.launch {
            try {
                _searchQuery
                    .debounce(SEARCH_DELAY)
                    .combine(getTodoItemsUseCase.items()) { query, items ->
                        if (query.isBlank()) {
                            items
                        } else {
                            items.filter {
                                it.title.contains(query, ignoreCase = true)
                            }
                        }
                    }.collectLatest { filteredItems ->
                        _uiState.value = HomeState.Success(filteredItems)
                    }
            } catch (cancellationException: CancellationException) {
                Log.d(TAG, "Previous search job is cancelled: ")
            } catch (exception: Exception) {
                _uiState.value = HomeState.Error(exception.message ?: "Something went wrong")
            }
        }
    }
}