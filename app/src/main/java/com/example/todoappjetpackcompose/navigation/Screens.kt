package com.example.todoappjetpackcompose.navigation

import androidx.navigation.NavHostController
import com.example.todoappjetpackcompose.util.Action
import com.example.todoappjetpackcompose.util.Constants.LIST_SCREEN

class Screens(navHostController: NavHostController) {
    val list: (Action) -> Unit = { action ->
        navHostController.navigate("list/${action.name}") {
            /**
             * inclusive , means that whenever we navigate from our task composable to
             * our list composable,i wan to pop up to a list screen and basically remove
             * our task composable from the backstack and
             */
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }

    /**
     * As per official doc, here instead of the task object, we passing that task ID,
     * so that using that ID , we can get respective task object from a db
     */
    val task : (Int) -> Unit = {taskId ->
        navHostController.navigate("task/$taskId")
    }
}