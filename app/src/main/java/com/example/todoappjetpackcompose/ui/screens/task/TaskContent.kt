package com.example.todoappjetpackcompose.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoappjetpackcompose.R
import com.example.todoappjetpackcompose.component.PriorityDropDown
import com.example.todoappjetpackcompose.data.models.Priority
import com.example.todoappjetpackcompose.ui.theme.LARGE_PADDING
import com.example.todoappjetpackcompose.ui.theme.MEDIUM_PADDING

@Composable
fun TaskContent(
    contentPaddingValues: PaddingValues,
    title: String,
    onTitleChange : (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(contentPaddingValues)
            .padding(LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(id = R.string.title))},
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true
        )
        Spacer(
            modifier = Modifier.height(MEDIUM_PADDING)
        )
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = { onPrioritySelected(it) }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            textStyle = MaterialTheme.typography.bodyLarge,
            label = { Text(text = stringResource(id = R.string.description))}
        )
    }
}

@Preview
@Composable
fun TaskContentPreview() {
    TaskContent(
        contentPaddingValues = PaddingValues(),
        title = "",
        onTitleChange = {},
        description = "",
        onDescriptionChange = {},
        priority = Priority.HIGH,
        onPrioritySelected = {}
    )
}