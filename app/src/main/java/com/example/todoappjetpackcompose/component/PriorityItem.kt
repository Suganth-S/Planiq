package com.example.todoappjetpackcompose.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoappjetpackcompose.data.models.Priority
import com.example.todoappjetpackcompose.ui.theme.LARGE_PADDING
import com.example.todoappjetpackcompose.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun PriorityItem(priority: Priority) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)){
            drawCircle(color = priority.color)
        }
        Spacer(modifier = Modifier.width(LARGE_PADDING))
        Text(text = priority.name, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
@Preview
fun PriorityItemPreview() {
    PriorityItem(priority = Priority.LOW)
}