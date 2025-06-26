package com.ingenioustech.testapp1

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await // Import for await()

class TaskRepository(
    private val firestore: FirebaseFirestore
) {
    private val tasksCollection = firestore.collection("tasks")

    suspend fun addTask(task: Task) {
        val id = task.id ?: throw IllegalArgumentException("Task id cannot be null")
        tasksCollection.document(id).set(task).await() // Use await for suspend functions
    }

    // Modified getTasks to return List<Task> directly
    suspend fun getTasks(): List<Task> {
        return try {
            val documents = tasksCollection.get().await() // Use await to get the result
            documents.map { it.toObject(Task::class.java) }
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