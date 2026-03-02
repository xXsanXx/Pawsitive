package com.nastena.pawsitive.ui.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("USER") } // по умолчанию USER

    LaunchedEffect(state) {
        if (state is RegisterState.Success) {
            onRegisterSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "Регистрация",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Роль: ")
                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { role = "USER" },
                    enabled = state !is RegisterState.Loading
                ) {
                    Text("USER")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { role = "SHELTER" },
                    enabled = state !is RegisterState.Loading
                    ) {
                        Text("SHELTER")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.register(email, password, role)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = state !is RegisterState.Loading
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = onBack,
                enabled = state !is RegisterState.Loading
            ) {
                Text("Back to Login")
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (val currentState = state) {

                is RegisterState.Loading -> {
                    CircularProgressIndicator()
                }

                is RegisterState.Error -> {

                    val errorMessage = when (val error = currentState.error) {
                        RegisterError.EmptyFields -> "Заполните все поля"
                        RegisterError.InvalidEmail -> "Некорректный email"
                        RegisterError.WeakPassword -> "Пароль минимум 12 символов, должен содержать буквы и цифры"
                        RegisterError.InvalidRole -> "Выберите роль"
                        RegisterError.EmailAlreadyExists -> "Email уже существует"
                        is RegisterError.ServerError -> error.message
                        RegisterError.Unknown -> "Неизвестная ошибка"
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {}
            }
        }

    }
}