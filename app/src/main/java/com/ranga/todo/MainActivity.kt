package com.ranga.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ranga.todo.ui.navigation.AddTodo
import com.ranga.todo.ui.navigation.Error
import com.ranga.todo.ui.navigation.Home
import com.ranga.todo.ui.navigation.addTodoDestination
import com.ranga.todo.ui.navigation.errorDestination
import com.ranga.todo.ui.navigation.homeDestination
import com.ranga.todo.ui.theme.TodoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            TodoTheme {
                NavHost(navController = navController, startDestination = Home) {
                    homeDestination(
                        onNavigateToAddTodo = { navController.navigate(AddTodo) }
                    )
                    addTodoDestination(
                        onNavigateToBack = { navController.popBackStack() },
                        onNavigateToErrorDialog = {
                            navController.popBackStack()
                            navController.navigate(Error(it))
                        },
                    )
                    errorDestination(
                        onDismiss = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}