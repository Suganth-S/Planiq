package com.example.todoappjetpackcompose.ui.screens.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.todoappjetpackcompose.R
import com.example.todoappjetpackcompose.ui.theme.taskItemBackgroundColor
import com.example.todoappjetpackcompose.ui.theme.topAppBarBackgrounColor
import com.example.todoappjetpackcompose.ui.theme.topAppBarContentColor
import com.example.todoappjetpackcompose.util.Action

@Composable
fun TaskAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    NewTaskAppBar(navigateToListScreen = navigateToListScreen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        title = {
                Text(
                    text = stringResource(id = R.string.add_task),
                    color = MaterialTheme.colorScheme.topAppBarContentColor
                )
        },
        navigationIcon = {
            BackAction(onBackClicked = navigateToListScreen)
        },
        actions = {
            AddAction(onAddClicked = navigateToListScreen)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgrounColor
        )
    )
}

@Composable
fun BackAction(
    onBackClicked : (Action) -> Unit
) {
    IconButton(onClick = { }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back_arrow),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun AddAction(
    onAddClicked : (Action) -> Unit
) {
    IconButton(onClick = { }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.add_task),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@PreviewLightDark
@Composable
fun NewTaskAppBarPreview() {
    NewTaskAppBar(navigateToListScreen = {})
}