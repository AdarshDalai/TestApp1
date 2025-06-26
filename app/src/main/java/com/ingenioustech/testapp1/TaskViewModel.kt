package com.ingenioustech.testapp1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class TaskViewModel: ViewModel() {
    private val repository = TaskRepository(FirebaseFirestore.getInstance())
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            try {
                _tasks.value = repository.getTasks()
            } catch (e: Exception) {
                // Handle the error, e.g., log it or show a message to the user
                e.printStackTrace()
            }
        }
    }

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            try {
                val task = Task(id = UUID.randomUUID().toString(), title = title, description = description)
                repository.addTask(task)
                fetchTasks() // Refresh the task list
            } catch (e: Exception) {
                // Handle the error, e.g., log it or show a message to the user
                e.printStackTrace()
            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                repository.updateTask(task)
                fetchTasks() // Refresh the task list
            } catch (e: Exception) {
                // Handle the error, e.g., log it or show a message to the user
                e.printStackTrace()
            }
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            try {
                repository.deleteTask(taskId)
                fetchTasks() // Refresh the task list
            } catch (e: Exception) {
                // Handle the error, e.g., log it or show a message to the user
                e.printStackTrace()
            }
        }
    }
}