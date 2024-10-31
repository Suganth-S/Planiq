package com.example.todoappjetpackcompose.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.todoappjetpackcompose.R
import com.example.todoappjetpackcompose.ui.theme.DarkGray
import com.example.todoappjetpackcompose.ui.theme.Purple700
import com.example.todoappjetpackcompose.ui.theme.Teal200
import com.example.todoappjetpackcompose.ui.viewmodels.SharedViewModel
import com.example.todoappjetpackcompose.util.SearchAppbarState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    action: String,
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

    /**
     * collectAsState() => Will observe Flow from the composable function
     */
    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchAppbarState: SearchAppbarState by sharedViewModel.searchAppBarState
    val searchTextState : String by sharedViewModel.searchTextState

    Scaffold(
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
                navigateToTaskScreen,
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