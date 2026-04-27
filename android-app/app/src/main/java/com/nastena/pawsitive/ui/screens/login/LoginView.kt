package com.nastena.pawsitive.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nastena.pawsitive.R
import com.nastena.pawsitive.ui.common.validation.ValidationState

@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel
) {

    val emailState by viewModel.emailState.collectAsState()
    val passwordState by viewModel.passwordState.collectAsState()

    LoginView(
        modifier = modifier,
        emailState = emailState,
        passwordState = passwordState,
        onEvent = { viewModel.onViewEvent(it) }
    )
}

@Composable
private fun LoginView(
    modifier: Modifier = Modifier,
    emailState: LoginState.Email,
    passwordState: LoginState.Password,
    onEvent: (LoginEvents) -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier
                .padding(24.dp)
                .widthIn(max = 420.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(48.dp))

            Icon(
                imageVector = Icons.Default.Pets,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(72.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.login_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = emailState.text,
                onValueChange = {
                    onEvent(LoginEvents.Email.TextUpdated(it))
                },
                label = { Text(stringResource(R.string.login_email_label)) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = emailState.validation != ValidationState.Valid,
                supportingText = {
                    if (emailState.validation != ValidationState.Valid) {
                        Text(stringResource(R.string.login_email_invalid))
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = passwordState.text,
                onValueChange = {
                    onEvent(LoginEvents.Password.TextUpdated(it))
                },
                label = { Text(stringResource(R.string.login_password_label)) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation =
                    if (passwordState.isVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                isError = passwordState.validation != ValidationState.Valid,
                supportingText = {
                    if (passwordState.validation != ValidationState.Valid) {
                        Text(stringResource(R.string.login_password_invalid))
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onEvent(LoginEvents.Password.EyeClicked)
                        }
                    ) {
                        Icon(
                            imageVector =
                                if (passwordState.isVisible)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                }
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { onEvent(LoginEvents.LoginClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(stringResource(R.string.login_submit))
            }

            Spacer(Modifier.height(12.dp))

            TextButton(
                onClick = {
                    onEvent(LoginEvents.GoToRegistrationClicked)
                }
            ) {
                Text(stringResource(R.string.login_go_to_register))
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}


//package com.nastena.pawsitive.ui.screens.login
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.unit.dp
//import com.nastena.pawsitive.R
//
//@Composable
//fun LoginView(
//    modifier: Modifier = Modifier,
//    viewModel: LoginViewModel
//) {
//
//    val emailState by viewModel.emailFieldState.collectAsState()
//    val passwordState by viewModel.passwordFieldState.collectAsState()
//
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//
//            Text(
//                text = stringResource(R.string.login_title),
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            OutlinedTextField(
//                value = emailState.text,
//                onValueChange = { text ->
//                    viewModel.onViewEvent(LoginViewEvents.Email.TextUpdated(text))
//                },
//                isError = !emailState.isValid,
//                label = { Text(stringResource(R.string.login_email_label)) },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            OutlinedTextField(
//                value = passwordState.text,
//                onValueChange = { text ->
//                    viewModel.onViewEvent(LoginViewEvents.Password.TextUpdate(text))
//                },
//                label = { Text(stringResource(R.string.login_password_label)) },
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//                visualTransformation = if (passwordState.isVisible) {
//                    VisualTransformation.None
//                } else {
//                    PasswordVisualTransformation()
//                },
//                isError = !passwordState.isValid,
//                trailingIcon = {
//                    IconButton(
//                        onClick = { viewModel.onViewEvent(LoginViewEvents.Password.EyeClicked) }
//                    ) {
//                        Icon(
//                            imageVector = if (passwordState.isVisible) {
//                                Icons.Default.Visibility
//                            } else {
//                                Icons.Default.VisibilityOff
//                            },
//                            contentDescription = if (passwordState.isVisible) {
//                                stringResource(R.string.login_password_hide_description)
//                            } else {
//                                stringResource(R.string.login_password_show_description)
//                            }
//                        )
//                    }
//                }
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Button(
//                onClick = { viewModel.onViewEvent(LoginViewEvents.LoginClicked) },
//                modifier = Modifier.fillMaxWidth(),
//            ) {
//                Text(stringResource(R.string.login_submit))
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            TextButton(
//                onClick = { viewModel.onViewEvent(LoginViewEvents.GoToRegistrationClicked) },
//            ) {
//                Text(stringResource(R.string.login_go_to_register))
//            }
//        }
//    }
//}