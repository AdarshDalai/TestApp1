package com.ingenioustech.testapp1.task.domain.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.ingenioustech.testapp1.task.data.model.Task
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val tasksCollection = firestore.collection("tasks")
    //implementing the shared preferences for the task repository

    suspend fun addTask(task: Task) {
        val id = task.id ?: throw IllegalArgumentException("Task id cannot be null")
        tasksCollection.document(id).set(task).await() // Use await for suspend functions
    }

    // Modified getTasks to return List<Task> directly
    suspend fun getTasks(): List<Task> {
        return try {
            val documents = tasksCollection.get().await() // Use await to get the result
            documents.map { it.toObject(Task::class.java)
                //Store the  task in the Task object in the shared preferences

            }
        } catch (e: Exception) {
            // Handle the error appropriately, maybe rethrow or return empty list
            e.printStackTrace()
            emptyList() // Or throw e
        }
    }

    suspend fun updateTask(task: Task) {
        val id = task.id ?: throw IllegalArgumentException("Task id cannot be null")
        tasksCollection.document(id).set(task).await()
    }

    suspend fun deleteTask(taskId: String) {
        tasksCollection.document(taskId).delete().await()
    }
}

/*
* store the data when you getTasks or addTasks
* delete or update accordingly
* This will ensure that the data is always in sync with the Firestore database.
* */