package com.nastena.pawsitive.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nastena.pawsitive.R

@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel
) {

    val emailState by viewModel.emailFieldState.collectAsState()
    val passwordState by viewModel.passwordFieldState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = stringResource(R.string.login_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = emailState.text,
                onValueChange = { text ->
                    viewModel.onViewEvent(LoginViewEvents.Email.TextUpdated(text))
                },
                isError = !emailState.isValid,
                label = { Text(stringResource(R.string.login_email_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = passwordState.text,
                onValueChange = { text ->
                    viewModel.onViewEvent(LoginViewEvents.Password.TextUpdate(text))
                },
                label = { Text(stringResource(R.string.login_password_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordState.isVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                isError = !passwordState.isValid,
                trailingIcon = {
                    IconButton(
                        onClick = { viewModel.onViewEvent(LoginViewEvents.Password.EyeClicked) }
                    ) {
                        Icon(
                            imageVector = if (passwordState.isVisible) {
                                Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },
                            contentDescription = if (passwordState.isVisible) {
                                stringResource(R.string.login_password_hide_description)
                            } else {
                                stringResource(R.string.login_password_show_description)
                            }
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onViewEvent(LoginViewEvents.LoginClicked) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.login_submit))
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = { viewModel.onViewEvent(LoginViewEvents.GoToRegistrationClicked) },
            ) {
                Text(stringResource(R.string.login_go_to_register))
            }
        }
    }
}