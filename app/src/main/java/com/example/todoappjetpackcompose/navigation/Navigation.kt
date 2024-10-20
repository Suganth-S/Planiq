package com.example.todoappjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.todoappjetpackcompose.navigation.destinations.listComposable
import com.example.todoappjetpackcompose.navigation.destinations.taskComposable
import com.example.todoappjetpackcompose.util.Constants.LIST_SCREEN

@Composable
fun SetupNavigation(
    navHostController: NavHostController
) {
    /**
     * With this remember comp function, we want to save our back stack of our composable
     * screens through out our application
     */
    val screen = remember(navHostController) {
            Screens(navHostController = navHostController)
    }

    NavHost(navController = navHostController, startDestination = LIST_SCREEN ){
        listComposable(
            navigateToTaskScreen = screen.task
        )
        taskComposable (
            navigateToListScreen = screen.list
        )
    }
}