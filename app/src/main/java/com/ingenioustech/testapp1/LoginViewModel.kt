package com.ingenioustech.testapp1

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth


class LoginViewModel: ViewModel() {
    private lateinit var auth: FirebaseAuth

    init {
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
    }

    fun signup(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d(TAG, "createUserWithEmail:success")
                // User registration successful
                // You can navigate to the next screen or show a success message
            }
            .addOnFailureListener {
                exception ->
                Log.w(TAG, "createUserWithEmail:failure", exception)
                // User registration failed
                // You can show an error message to the user
            }
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d(TAG, "signInWithEmail:success")
                // User login successful
                // You can navigate to the next screen or show a success message
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "signInWithEmail:failure", exception)
                // User login failed
                // You can show an error message to the user
            }
    }

}