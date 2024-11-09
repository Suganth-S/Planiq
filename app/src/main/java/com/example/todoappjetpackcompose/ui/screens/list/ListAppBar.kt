package com.example.todoappjetpackcompose.ui.screens.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoappjetpackcompose.R
import com.example.todoappjetpackcompose.component.DisplayAlertDialog
import com.example.todoappjetpackcompose.component.PriorityItem
import com.example.todoappjetpackcompose.data.models.Priority
import com.example.todoappjetpackcompose.ui.theme.LARGE_PADDING
import com.example.todoappjetpackcompose.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.todoappjetpackcompose.ui.theme.topAppBarBackgrounColor
import com.example.todoappjetpackcompose.ui.theme.topAppBarContentColor
import com.example.todoappjetpackcompose.ui.viewmodels.SharedViewModel
import com.example.todoappjetpackcompose.util.Action
import com.example.todoappjetpackcompose.util.SearchAppbarState
import com.example.todoappjetpackcompose.util.TrailingIconState

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppbarState: SearchAppbarState,
    searchTextState: String
) {
    when(searchAppbarState){
        SearchAppbarState.CLOSED -> {
            DefaultListAppBar(onSearchClicked = {
                sharedViewModel.searchAppBarState.value = SearchAppbarState.OPENED
            }, onSortAction = {}, onDeleteAllConfirmed = {
                sharedViewModel.action.value = Action.DELETE_ALL
            })
        }
        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = {sharedViewModel.searchTextState.value = it},
                onCloseClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppbarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                },
                onSearchClicked = {
                    sharedViewModel.searchDatabase(searchQuery = it)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortAction: (Priority) -> Unit,
    onDeleteAllConfirmed: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.tasks), color = MaterialTheme.colorScheme.topAppBarContentColor) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgrounColor
        ),
        actions = {
            ListBarActions(
                onSearchClicked,
                onSortAction,
                onDeleteAllConfirmed
            )
        }
    )
}

@Composable
fun ListBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllConfirmed: () -> Unit
) {
    var openDialog by remember {
        mutableStateOf(false)
    }

    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_all_tasks),
        message = stringResource(id = R.string.delete_all_tasks_confirmation),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = {onDeleteAllConfirmed()}
    )
    SearchAction(onSearchClicked)
    SortAction(onSortAction = onSortClicked)
    DeleteAction (onDeleteAllConfirmed = {
        openDialog = true
    })
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(onClick = { onSearchClicked() }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = com.example.todoappjetpackcompose.R.string.search_task),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun SortAction(
    onSortAction: (Priority) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = stringResource(id = R.string.sort_task),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { PriorityItem(priority = Priority.LOW) },
                onClick = {
                    expanded = false
                    onSortAction(Priority.LOW)
                })
            DropdownMenuItem(
                text = {
                    PriorityItem(priority = Priority.HIGH)
                    onSortAction(Priority.HIGH)
                },
                onClick = { expanded = false })
            DropdownMenuItem(
                text = {
                    PriorityItem(priority = Priority.NONE)
                    onSortAction(Priority.NONE)
                },
                onClick = { expanded = false })
        }
    }
}

@Composable
fun DeleteAction(
    onDeleteAllConfirmed : () -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vert_menu),
            contentDescription = stringResource(id = R.string.delete_all_action),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(id = R.string.delete_all_action),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = LARGE_PADDING)
                    ) },
                onClick = {
                    expanded = false
                    onDeleteAllConfirmed()
                })
        }
    }
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.READY_TO_DELETE)
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        shadowElevation = 4.dp,
        color = MaterialTheme.colorScheme.topAppBarBackgrounColor
    ) {
        TextField(
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(modifier = Modifier.alpha(0.5f),text = stringResource(id = R.string.search), color = Color.White)
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.topAppBarContentColor,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {  },
                    modifier = Modifier.alpha(0.38f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search_icon),
                        tint = MaterialTheme.colorScheme.topAppBarContentColor
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    when(trailingIconState){
                        TrailingIconState.READY_TO_DELETE-> {
                            onTextChange("")
                            trailingIconState = TrailingIconState.READY_TO_CLOSE
                        }
                        TrailingIconState.READY_TO_CLOSE -> {
                            if (text.isNotEmpty()){
                                onTextChange("")
                            } else {
                                onCloseClicked()
                                trailingIconState = TrailingIconState.READY_TO_DELETE 
                            }
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.close_icon),
                        tint = MaterialTheme.colorScheme.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors =TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.topAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )
    }
}

@Composable
@Preview
fun DefaultListAppBarPreview() {
    DefaultListAppBar(onSearchClicked = {}, onSortAction = {}, onDeleteAllConfirmed = {})
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}