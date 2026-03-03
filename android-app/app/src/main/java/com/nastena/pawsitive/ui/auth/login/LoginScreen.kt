package com.nastena.pawsitive.ui.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nastena.pawsitive.data.datastore.TokenManager
import com.nastena.pawsitive.data.remote.dto.Role

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel,
    tokenManager: TokenManager,
    onNavigateToRegister: () -> Unit

) {

    val state by viewModel.state.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state) {
        if (state is LoginState.Success) {

            val role = tokenManager.getRole()

            when (role) {
                Role.USER -> {
                    navController.navigate("user_home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
                Role.SHELTER -> {
                    navController.navigate("shelter_home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
                null -> {}
            }
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
                text = "Вход",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,

            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,

            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.login(email, password)
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = onNavigateToRegister,
            ) {
                Text("Нет аккаунта? Зарегистрироваться")
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (val currentState = state) {

                is LoginState.Loading -> {
                    CircularProgressIndicator()
                }

                is LoginState.Error -> {

                    val errorMessage = when (val error = currentState.error) {

                        LoginError.EmptyFields ->
                            "Заполните все поля"

                        LoginError.InvalidEmail ->
                            "Некорректный email"

                        LoginError.WeakPassword ->
                            "Пароль минимум 12 символов"

                        LoginError.InvalidCredentials ->
                            "Неверный email или пароль"

                        LoginError.NetworkError ->
                            "Нет соединения с интернетом"

                        is LoginError.ServerError ->
                            error.message

                        LoginError.Unknown ->
                            "Неизвестная ошибка"
                    }

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