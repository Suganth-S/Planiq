package com.example.todoappjetpackcompose.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoappjetpackcompose.data.models.ToDoTask
import com.example.todoappjetpackcompose.data.repositories.ToDoRepository
import com.example.todoappjetpackcompose.util.SearchAppbarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository
): ViewModel() {

    val searchAppBarState : MutableState<SearchAppbarState> = mutableStateOf(SearchAppbarState.CLOSED)
    val searchTextState : MutableState<String> = mutableStateOf("")
    private val _allTasks = MutableStateFlow<List<ToDoTask>>(emptyList())
    val allTasks : StateFlow<List<ToDoTask>> = _allTasks

    fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTask.collect{
                _allTasks.value = it
            }
        }
    }


}