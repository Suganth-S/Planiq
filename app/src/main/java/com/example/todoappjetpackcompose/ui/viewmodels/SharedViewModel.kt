package com.example.todoappjetpackcompose.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoappjetpackcompose.data.models.Priority
import com.example.todoappjetpackcompose.data.models.ToDoTask
import com.example.todoappjetpackcompose.data.repositories.ToDoRepository
import com.example.todoappjetpackcompose.ui.screens.list.TaskItem
import com.example.todoappjetpackcompose.util.RequestState
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

    val id : MutableState<Int> = mutableIntStateOf(0)
    val title : MutableState<String> = mutableStateOf("")
    val description : MutableState<String> = mutableStateOf("")
    val priority : MutableState<Priority> = mutableStateOf(Priority.LOW)


    val searchAppBarState : MutableState<SearchAppbarState> = mutableStateOf(SearchAppbarState.CLOSED)
    val searchTextState : MutableState<String> = mutableStateOf("")
    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks : StateFlow<RequestState<List<ToDoTask>>> = _allTasks
    private val _selectedTasks : MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTasks

    fun addTask(){
        viewModelScope.launch {
            val task =ToDoTask(
                id = 0,
                title = "Android",
                description = "Jetpack compose",
                priority = Priority.MEDIUM
            )
            repository.addTask(task)
        }
    }
    fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch {
                repository.getAllTask.collect{
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }
    }

    fun getSelectedTask(taskId : Int){
        viewModelScope.launch {
            repository.getSelectedTask(taskId).collect{task ->
                _selectedTasks.value = task
            }
        }
    }

    fun updateTaskFields(selectedTask: ToDoTask?){
        if (selectedTask != null){
            id.value = selectedTask.id
            title.value = selectedTask.title
            description.value = selectedTask.description
            priority.value = selectedTask.priority
        } else {
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }


}