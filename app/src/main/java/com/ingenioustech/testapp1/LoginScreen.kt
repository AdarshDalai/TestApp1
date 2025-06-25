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


@Composable
fun LoginScreen() {
    
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var viewModel: LoginViewModel = viewModel()
    var alertBoxState by remember { mutableStateOf(false) }
    
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
            viewModel.login(email, password)
            alertBoxState = true
        }) {
            Text("Login")
        }
        Button(onClick = {
            viewModel.signup(email, password)
            alertBoxState = true
        }) {
            Text("Register")
        }
    }
    if (alertBoxState) {
        AlertBoxCustom(
            title = "Alert",
            message = "Successfully logged in or registered",
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