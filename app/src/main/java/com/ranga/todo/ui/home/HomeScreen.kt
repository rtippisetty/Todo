package com.ranga.todo.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.ranga.todo.R
import com.ranga.todo.api.model.Todo
import com.ranga.todo.ui.common.Divider
import com.ranga.todo.ui.common.ErrorDialog
import com.ranga.todo.ui.common.FloatingRoundButton
import com.ranga.todo.ui.common.ProgressIndicator
import com.ranga.todo.ui.common.TodoTopAppBar
import com.ranga.todo.ui.theme.TodoTheme

@Composable
fun HomeScreen(
    onNavigateToAddTodo: () -> Unit,
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    ScreenContent(
        homeState = uiState,
        onAddItem = onNavigateToAddTodo,
        searchQuery = searchQuery,
        onSearch = viewModel::onSearch
    )
}

@Composable
private fun ScreenContent(
    homeState: HomeState,
    onAddItem: () -> Unit,
    searchQuery: String = "",
    onSearch: (query: String) -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = { TodoTopAppBar(title = stringResource(id = R.string.todo_list)) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearch,
                    label = { Text(stringResource(id = R.string.search_query)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = dimensionResource(id = R.dimen.margin_medium),
                        ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    )
                )
                // List display section
                when (homeState) {
                    HomeState.Loading -> ProgressIndicator()
                    is HomeState.Success ->
                        //List items section
                        if (homeState.items.isEmpty()) {
                            EmptyItemsMessage(modifier = Modifier.fillMaxSize())
                        } else {
                            TodoItems(
                                items = homeState.items
                            )
                        }

                    is HomeState.Error -> ErrorDialog(
                        modifier = Modifier.fillMaxSize(),
                        description = homeState.message,
                        onDismissEnabled = false
                    )
                }
            }

            //Add button section
            FloatingRoundButton(
                modifier = Modifier
                    .padding(all = dimensionResource(id = R.dimen.margin_large))
                    .size(dimensionResource(id = R.dimen.floating_button_size))
                    .align(Alignment.BottomEnd),
                onButtonClick = onAddItem
            )
        }
    }
}

@Composable
private fun EmptyItemsMessage(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.empty_todo_list),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun TodoItems(
    modifier: Modifier = Modifier,
    items: List<Todo>
) {
    LazyColumn(
        modifier = modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.margin_medium),
                vertical = dimensionResource(id = R.dimen.margin_medium),
            )
    ) {
        items(items.size) { index ->
            Text(
                text = items[index].title,
                style = MaterialTheme.typography.bodyLarge,
            )
            if (index < items.size - 1) {
                //Add divider for all items except the last one
                Divider(
                    modifier = modifier
                )
            }
        }
    }
}

@Preview
@Composable
private fun TodoListScreenPreview(
    @PreviewParameter(HomeStateProvider::class) homeState: HomeState
) {
    TodoTheme {
        ScreenContent(
            homeState = homeState,
            onAddItem = {},
            onSearch = {}
        )
    }
}

private class HomeStateProvider : PreviewParameterProvider<HomeState> {
    override val values = sequenceOf(
        HomeState.Loading,
        HomeState.Success(items = listOf(Todo("1","Title 1", false), Todo("2","Title 2", false))),
        HomeState.Error("Error message")
    )
}