package com.example.todoappjetpackcompose.ui.screens.list

import android.graphics.drawable.Icon
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.todoappjetpackcompose.data.models.Priority
import com.example.todoappjetpackcompose.data.models.ToDoTask
import com.example.todoappjetpackcompose.ui.theme.HighPriorityColor
import com.example.todoappjetpackcompose.ui.theme.LARGEST_PADDING
import com.example.todoappjetpackcompose.ui.theme.LARGE_PADDING
import com.example.todoappjetpackcompose.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.todoappjetpackcompose.ui.theme.TASK_ITEM_ELEVATION
import com.example.todoappjetpackcompose.ui.theme.taskItemBackgroundColor
import com.example.todoappjetpackcompose.ui.theme.taskItemTextColor
import com.example.todoappjetpackcompose.util.RequestState
import com.example.todoappjetpackcompose.util.SearchAppbarState
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.todoappjetpackcompose.R
import com.example.todoappjetpackcompose.util.Action
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListContent(
    tasks: RequestState<List<ToDoTask>>,
    searchedTasks: RequestState<List<ToDoTask>>,
    highPriorityTasks: List<ToDoTask>,
    lowPriorityTasks: List<ToDoTask>,
    sortStates: RequestState<Priority>,
    contentPaddingValues: PaddingValues,
    searchAppbarState: SearchAppbarState,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (sortStates is RequestState.Success) {
        when {
            searchAppbarState == SearchAppbarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success) {
                    HandleListContent(
                        task = searchedTasks.data,
                        contentPaddingValues = contentPaddingValues,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreen = navigateToTaskScreen
                    )
                }
            }

            sortStates.data == Priority.NONE -> {
                if (tasks is RequestState.Success) {
                    HandleListContent(
                        task = tasks.data,
                        contentPaddingValues = contentPaddingValues,
                        onSwipeToDelete = onSwipeToDelete,
                        navigateToTaskScreen = navigateToTaskScreen
                    )
                }
            }

            sortStates.data == Priority.LOW ->{
                HandleListContent(
                    task = lowPriorityTasks,
                    contentPaddingValues = contentPaddingValues,
                    onSwipeToDelete= onSwipeToDelete,
                    navigateToTaskScreen = navigateToTaskScreen
                )
            }
            sortStates.data == Priority.HIGH ->{
                HandleListContent(
                    task = highPriorityTasks,
                    contentPaddingValues = contentPaddingValues,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreen = navigateToTaskScreen
                )
            }
        }
    }
}

@Composable
fun HandleListContent(
    task: List<ToDoTask>,
    contentPaddingValues: PaddingValues,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if (task.isEmpty()){
        EmptyContent()
    } else{
        DisplayTasks(
            tasks = task,
            contentPaddingValues = contentPaddingValues,
            onSwipeToDelete = onSwipeToDelete,
            navigateToTaskScreen = navigateToTaskScreen
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    contentPaddingValues: PaddingValues,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn(contentPadding = contentPaddingValues){
        items(
            items = tasks,
            //it represent a stable and unique keys for an item,
            //using same key for multiple item in list is not allowed
            key = {task ->
                task.id
            }
        ){task ->
            val dismissState = rememberSwipeToDismissBoxState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart && dismissState.progress == 1f
            if (isDismissed && dismissDirection == SwipeToDismissBoxValue.EndToStart){
                val scope = rememberCoroutineScope()
                SideEffect {
                    scope.launch {
                        delay(300)
                        onSwipeToDelete(Action.DELETE, task)
                    }
                }
            }
            val degree by animateFloatAsState(
                targetValue = if (dismissState.progress in 0f..0.5f) 0f else -45f,
                label = "Degree Animation"
            )

            var itemAppeared by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(key1 = true ){
                itemAppeared = true
            }

            AnimatedVisibility(
                visible = itemAppeared && !isDismissed,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            ) {
                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = { RedBackground(degrees = degree) }) {
                    TaskItem(toDoTask = task, navigateToTaskScreen = navigateToTaskScreen)
                }
            }
        }
    }
}


@Composable
fun RedBackground(degrees: Float) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(HighPriorityColor)
        .padding(horizontal = LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd) {
        Icon(
            modifier = Modifier.rotate(degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = Color.White
        )
    }
}

@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreen : (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.taskItemBackgroundColor,
        shape = RectangleShape,
        shadowElevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(toDoTask.id)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = toDoTask.title,
                    color = MaterialTheme.colorScheme.taskItemTextColor,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box (modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), contentAlignment = Alignment.TopEnd){
                    Canvas(modifier = Modifier
                        .size(PRIORITY_INDICATOR_SIZE)){
                        drawCircle(color = toDoTask.priority.color)
                    }
                }
            }
            Text(
                text = toDoTask.description,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.taskItemTextColor,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
@PreviewLightDark
fun TaskItemPreview() {
    TaskItem(
        toDoTask = ToDoTask(
            id = 0,
            title = "Title",
            description = "Task Item description",
            priority = Priority.MEDIUM
        ),
        navigateToTaskScreen = {}
    )

}