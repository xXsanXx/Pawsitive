package com.nastena.pawsitive.ui.auth

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    viewModel: LoginViewModel
) {
    val state by viewModel.state.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {Text ("Email")}
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            label = {Text("Password")}
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {viewModel.login(email, password)}
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is LoginState.Loading -> {
                CircularProgressIndicator()
            }

            is LoginState.Error -> {
                Text(
                    text = (state as LoginState.Error).message ?: "Ошибка",
                    color = MaterialTheme.colorScheme.error
                )
            }

            is LoginState.Success -> {
                Text("Успешный вход!")
            }

            else -> {}
        }
    }
}