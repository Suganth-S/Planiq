package com.example.todoappjetpackcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.todoappjetpackcompose.navigation.destinations.listComposable
import com.example.todoappjetpackcompose.navigation.destinations.taskComposable
import com.example.todoappjetpackcompose.ui.viewmodels.SharedViewModel
import com.example.todoappjetpackcompose.util.Constants.LIST_SCREEN_DEFAULT

@Composable
fun SetupNavigation(
    navHostController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    /**
     * With this remember comp function, we want to save our back stack of our composable
     * screens through out our application
     */
    val screen = remember(navHostController) {
        Screens(navHostController = navHostController)
    }

    NavHost(navController = navHostController, startDestination = LIST_SCREEN_DEFAULT) {
        listComposable(
            navigateToTaskScreen = screen.task,
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screen.list
        )
    }
}