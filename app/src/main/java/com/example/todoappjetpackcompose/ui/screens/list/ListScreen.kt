package com.example.todoappjetpackcompose.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.todoappjetpackcompose.R
import com.example.todoappjetpackcompose.ui.theme.DarkGray
import com.example.todoappjetpackcompose.ui.theme.MediumGray
import com.example.todoappjetpackcompose.ui.theme.Purple700
import com.example.todoappjetpackcompose.ui.theme.Teal200
import com.example.todoappjetpackcompose.ui.viewmodels.SharedViewModel
import com.example.todoappjetpackcompose.util.SearchAppbarState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    action: String,
    sharedViewModel: SharedViewModel
) {
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
        content = {},
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        },
        containerColor = scaffoldBackgroundColor()
    )
}

@Composable
fun ListFab(
    onFabClicked: (taskId:Int) -> Unit
) {
    FloatingActionButton(onClick = { onFabClicked(-1) }, shape = CircleShape, containerColor = fabBackgroundColor()) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.add_btn), tint = Color.White)
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