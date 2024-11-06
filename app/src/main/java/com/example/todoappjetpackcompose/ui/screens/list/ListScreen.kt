package com.example.todoappjetpackcompose.ui.screens.list

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.todoappjetpackcompose.R
import com.example.todoappjetpackcompose.ui.theme.DarkGray
import com.example.todoappjetpackcompose.ui.theme.Purple700
import com.example.todoappjetpackcompose.ui.theme.Teal200
import com.example.todoappjetpackcompose.ui.viewmodels.SharedViewModel
import com.example.todoappjetpackcompose.util.Action
import com.example.todoappjetpackcompose.util.SearchAppbarState
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    /**
     * When LaunchedEffect enters the composition it will launch
     * block into the composition's CoroutineContext. The coroutine
     * will be cancelled and re-launched when LaunchedEffect is recomposed
     * with a different key1. The coroutine will be cancelled when the LaunchedEffect
     * leaves the composition.
     */
    LaunchedEffect(key1 = true){
        sharedViewModel.getAllTasks()
    }

    val action by sharedViewModel.action
    /**
     * collectAsState() => Will observe Flow from the composable function
     */
    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()
    val searchAppbarState: SearchAppbarState by sharedViewModel.searchAppBarState
    val searchTextState : String by sharedViewModel.searchTextState
    val snackbarState = remember {
        SnackbarHostState()
    }

    DisplaySnackBar(
        scaffoldState = snackbarState,
        handleDatabaseAction = { sharedViewModel.handleDatabaseActions(action)},
        onUndoClicked = { sharedViewModel.action.value = it },
        taskTitle = sharedViewModel.title.value,
        action = action
    )

    Scaffold(
        snackbarHost = { SnackbarHost (hostState = snackbarState)},
        topBar = {
            ListAppBar(
                sharedViewModel,
                searchAppbarState,
                searchTextState
            )
        },
        content = {paddingValues ->
            ListContent(
                tasks = allTasks,
                contentPaddingValues = paddingValues,
                searchAppbarState = searchAppbarState,
                searchedTasks = searchedTasks,
                navigateToTaskScreen = navigateToTaskScreen
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        },
        containerColor = scaffoldBackgroundColor()
    )
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = { onFabClicked(-1) },
        shape = CircleShape,
        containerColor = fabBackgroundColor()
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_btn),
            tint = Color.White
        )
    }
}

@Composable
fun fabBackgroundColor(): Color {
    return if (isSystemInDarkTheme()) Purple700 else Teal200
}

@Composable
fun scaffoldBackgroundColor(): Color {
    return if (isSystemInDarkTheme()) DarkGray else Color.White
}

@Composable
fun DisplaySnackBar(
    scaffoldState: SnackbarHostState,
    handleDatabaseAction : () -> Unit,
    onUndoClicked: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {

    val context = LocalContext.current
    handleDatabaseAction()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action){
        if (action != Action.NO_ACTION){
            scope.launch {
                val snackBarResult = scaffoldState.showSnackbar(
                    message = setMessage(action, taskTitle, context),
                    actionLabel = setActionLabel(action),
                    duration = SnackbarDuration.Short
                )
                undoDeletedTask(
                    action = action,
                    snackbarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
        }
    }
}

private fun setActionLabel(action: Action): String{
    return if (action.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

private fun setMessage(action: Action, taskTitle: String, context: Context): String{
    return when(action){
        Action.DELETE_ALL -> {
            context.getString(R.string.all_tasks_removed)
        }
        else -> {
            "${action.name}: $taskTitle"
        }
    }
}

private fun undoDeletedTask(
    action: Action,
    snackbarResult: SnackbarResult,
    onUndoClicked : (Action) -> Unit
) {
    if (snackbarResult == SnackbarResult.ActionPerformed
        && action == Action.DELETE){
        onUndoClicked(Action.UNDO)
    }
}