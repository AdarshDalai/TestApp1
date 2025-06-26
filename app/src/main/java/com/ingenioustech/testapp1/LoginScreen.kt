package com.ingenioustech.testapp1

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.rememberCoroutineScope // Import rememberCoroutineScope
import kotlinx.coroutines.launch // Import launch

@Composable
fun LoginScreen() {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var viewModel: LoginViewModel = viewModel()
    var alertBoxState by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // Create a coroutine scope

    Column {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = {
            scope.launch { // Launch a coroutine
                viewModel.login(email, password)
                alertBoxState = true // Consider moving this based on login success
            }
        }) {
            Text("Login")
        }
        Button(onClick = {
            scope.launch { // Launch a coroutine
                viewModel.signup(email, password)
                alertBoxState = true // Consider moving this based on signup success
            }
        }) {
            Text("Register")
        }
    }
    if (alertBoxState) {
        AlertBoxCustom(
            title = "Alert",
            message = "Successfully logged in or registered", // You might want to customize this message
            onDismiss = { alertBoxState = false }
        )
    }

}

@Composable
fun AlertBoxCustom(
    title: String,
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}