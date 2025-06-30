package com.ingenioustech.testapp1.task.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.ingenioustech.testapp1.task.data.model.Task
import com.ingenioustech.testapp1.task.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
): ViewModel() {

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
                val task = Task(
                    id = UUID.randomUUID().toString(),
                    title = title,
                    description = description
                )
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

/*
* Fetch tasks from sharedPreferences as well as firestore and log about the receiving points like
* if it is already present in the shared preferences or not
* or else if you are getting it from the firestore */