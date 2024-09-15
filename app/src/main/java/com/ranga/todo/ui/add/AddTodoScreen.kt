package com.ranga.todo.ui.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.ranga.todo.R
import com.ranga.todo.ui.common.ProgressIndicator
import com.ranga.todo.ui.common.TodoTopAppBar

@Composable
fun AddTodoScreen(
    onNavigateToBack: () -> Unit,
    onNavigateToError: (failureMessage: String) -> Unit
) {
    val viewModel = hiltViewModel<AddItemViewModel>()
    val uiState by viewModel.saveItemState.collectAsState(AddItemState.None)

    ScreenContent(
        modifier = Modifier
            .fillMaxSize(),
        addItemState = uiState,
        onSaveItem = viewModel::saveItem,
        onNavigateToBack = onNavigateToBack,
        onSaveFiled = onNavigateToError,
        onSaveSuccess = onNavigateToBack
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    addItemState: AddItemState,
    onSaveItem: (title: String) -> Unit = {},
    onNavigateToBack: () -> Unit = {},
    onSaveFiled: (failureMessage: String) -> Unit = {},
    onSaveSuccess: () -> Unit = {}
) {
    var title by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TodoTopAppBar(
                title = stringResource(id = R.string.enter_new_item),
                onBackEnabled = true,
                onBackClick = onNavigateToBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = { Text("TODO Item") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = dimensionResource(id = R.dimen.margin_small),
                        horizontal = dimensionResource(id = R.dimen.margin_medium)
                    ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                )
            )
            Button(
                modifier = Modifier
                    .wrapContentSize()
                    .align(alignment = Alignment.CenterHorizontally),
                onClick = {
                    focusManager.clearFocus()
                    onSaveItem(title)
                },
                enabled = title.isNotBlank()
            ) {
                Text(stringResource(id = R.string.add_todo))
            }
            when (addItemState) {
                AddItemState.InProgress -> ProgressIndicator()
                AddItemState.Success -> onSaveSuccess()
                AddItemState.Error -> onSaveFiled(stringResource(id = R.string.error_message))
                AddItemState.None -> Unit
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenContentPreview(
    @PreviewParameter(AddItemStateProvider::class) addItemState: AddItemState
) {
    ScreenContent(
        addItemState = addItemState,
        onSaveItem = {}
    )
}

private class AddItemStateProvider: PreviewParameterProvider<AddItemState> {
    override val values: Sequence<AddItemState>
        get() = sequenceOf(
            AddItemState.None,
            AddItemState.InProgress,
            AddItemState.Success,
            AddItemState.Error
        )
}
