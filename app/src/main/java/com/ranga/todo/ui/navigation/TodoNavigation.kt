package com.ranga.todo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.toRoute
import com.ranga.todo.ui.add.AddTodoScreen
import com.ranga.todo.ui.common.ErrorDialog
import com.ranga.todo.ui.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
object AddTodo

@Serializable
data class Error(val message: String)

fun NavGraphBuilder.homeDestination(
    onNavigateToAddTodo: () -> Unit,
) {
    composable<Home> {
        HomeScreen(
            onNavigateToAddTodo = onNavigateToAddTodo,
        )
    }
}

fun NavGraphBuilder.addTodoDestination(
    onNavigateToBack: () -> Unit,
    onNavigateToErrorDialog: (String) -> Unit,
) {
    composable<AddTodo> {
        AddTodoScreen(
            onNavigateToBack = onNavigateToBack,
            onNavigateToError = onNavigateToErrorDialog
        )
    }
}

fun NavGraphBuilder.errorDestination(
    onDismiss: () -> Unit
) {
    dialog<Error> {
        ErrorDialog(
            description = (it.toRoute() as Error).message,
            onDismiss = onDismiss
        )
    }
}