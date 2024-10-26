package com.example.todoappjetpackcompose.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoappjetpackcompose.ui.screens.list.ListScreen
import com.example.todoappjetpackcompose.ui.viewmodels.SharedViewModel
import com.example.todoappjetpackcompose.util.Constants.LIST_ARGUMENT_KEY
import com.example.todoappjetpackcompose.util.Constants.LIST_SCREEN

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
){
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY){
            type = NavType.StringType
            defaultValue = "noAction"
        })
    ){backStackEntry ->
        val action = backStackEntry.arguments?.getString(LIST_ARGUMENT_KEY)?: "noAction"
        ListScreen(navigateToTaskScreen = navigateToTaskScreen, action = action, sharedViewModel)
    }
}