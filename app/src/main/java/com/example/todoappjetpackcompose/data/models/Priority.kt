package com.example.todoappjetpackcompose.data.models

import androidx.compose.ui.graphics.Color
import com.example.todoappjetpackcompose.ui.theme.HighPriorityColor
import com.example.todoappjetpackcompose.ui.theme.LowPriorityColor
import com.example.todoappjetpackcompose.ui.theme.MediumPriorityColor
import com.example.todoappjetpackcompose.ui.theme.NonePriorityColor

enum class Priority(var color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}