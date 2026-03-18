package com.nastena.pawsitive.ui.screens.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nastena.pawsitive.R
import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.ui.theme.PawsitiveTheme

@Composable
fun RegisterView(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel
) {
    // ------------- States --------------------
    val nameState: RegisterState.Name by viewModel.nameState.collectAsState()
    val emailState: RegisterState.Email by viewModel.emailState.collectAsState()
    val passwordState: RegisterState.Password by viewModel.passwordState.collectAsState()
    val confirmPasswordState: RegisterState.ConfirmPassword by viewModel.confirmPasswordState.collectAsState()
    val accountRoleMenuState: RegisterState.AccountRoleMenu by viewModel.accountRoleMenuState.collectAsState()

    RegisterView(
        modifier = modifier,
        nameState = nameState,
        emailState = emailState,
        passwordState = passwordState,
        confirmPasswordState = confirmPasswordState,
        accountRoleMenuState = accountRoleMenuState,
        onViewEvent = { event -> viewModel.onViewEvent(event) }
    )
}

@Composable
private fun RegisterView(
    modifier: Modifier = Modifier,
    nameState: RegisterState.Name,
    emailState: RegisterState.Email,
    passwordState: RegisterState.Password,
    confirmPasswordState: RegisterState.ConfirmPassword,
    accountRoleMenuState: RegisterState.AccountRoleMenu,
    onViewEvent: (RegisterViewEvents) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ------------- Title --------------------
            Text(
                text = stringResource(R.string.register_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ------------- Name --------------------
            AnimatedVisibility(
                visible = nameState.validation != RegisterState.Name.Validation.Valid
            ) {
                OutlinedTextField(
                    value = when (nameState.validation) {
                        RegisterState.Name.Validation.Empty -> stringResource(R.string.register_name_is_empty)
                        RegisterState.Name.Validation.InvalidFormat -> stringResource(R.string.register_name_invalid)
                        RegisterState.Name.Validation.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = nameState.text,
                onValueChange = { newText ->
                    onViewEvent(RegisterViewEvents.Name.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.register_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = nameState.validation != RegisterState.Name.Validation.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))



            // ------------- Email --------------------
            AnimatedVisibility(
                visible = emailState.validation != RegisterState.Email.Validation.Valid
            ) {
                OutlinedTextField(
                    value = when (emailState.validation) {
                        RegisterState.Email.Validation.Empty -> stringResource(R.string.register_email_is_empty)
                        RegisterState.Email.Validation.InvalidFormat -> stringResource(R.string.register_email_invalid)
                        RegisterState.Email.Validation.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = emailState.text,
                onValueChange = { newText ->
                    onViewEvent(RegisterViewEvents.Email.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.register_email_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = emailState.validation != RegisterState.Email.Validation.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))


            // ------------- Password --------------------

            AnimatedVisibility(
                visible = passwordState.validation != RegisterState.Password.Validation.Valid
            ) {
                OutlinedTextField(
                    value = when (passwordState.validation) {
                        RegisterState.Password.Validation.Empty -> stringResource(R.string.register_password_is_empty)
                        RegisterState.Password.Validation.InvalidFormat -> stringResource(R.string.register_password_invalid)
                        RegisterState.Password.Validation.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = passwordState.text,
                onValueChange = { newText ->
                    onViewEvent(RegisterViewEvents.Password.TextUpdated(newText))
                },

                label = { Text(stringResource(R.string.register_password_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = passwordState.validation != RegisterState.Password.Validation.Valid
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ------------- Confirm Password --------------------

            AnimatedVisibility(
                visible = !confirmPasswordState.isValid
            ) {
                OutlinedTextField(
                    value = if (confirmPasswordState.isValid) {
                        ""
                    } else {
                        stringResource(R.string.register_confirm_password_invalid)
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = confirmPasswordState.text,
                onValueChange = { newText ->
                    onViewEvent(RegisterViewEvents.ConfirmPassword.TextUpdated(newText))
                },

                label = { Text(stringResource(R.string.register_confirm_password_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = !confirmPasswordState.isValid
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ------------- Choosing roles --------------------
            AnimatedVisibility(
                visible = !accountRoleMenuState.isValid
            ) {
                OutlinedTextField(
                    value = if (accountRoleMenuState.isValid) {
                        ""
                    } else {
                        stringResource(R.string.register_choose_role_invalid)
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onViewEvent(RegisterViewEvents.AccountRoleMenu.ClickedMenu)
                }
            ) {
                val selectedRoleText: String = when (accountRoleMenuState.selected) {
                    AccountRole.USER -> stringResource(R.string.register_role_user)
                    AccountRole.SHELTER -> stringResource(R.string.register_role_shelter)
                    null -> stringResource(R.string.register_choose_role_label)
                }
                Text(
                    text = selectedRoleText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = accountRoleMenuState.isExpended,
                onDismissRequest = {
                    onViewEvent(RegisterViewEvents.AccountRoleMenu.MenuDismissed)
                }
            ) {
                // ------------- USER --------------------
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.register_role_user),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onViewEvent(
                            RegisterViewEvents.AccountRoleMenu.ClickedSelection(
                                AccountRole.USER
                            )
                        )
                    }
                )

                // ------------- SHELTER --------------------
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.register_role_shelter),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onViewEvent(
                            RegisterViewEvents.AccountRoleMenu.ClickedSelection(
                                AccountRole.SHELTER
                            )
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ------------- Buttons --------------------
            Button(
                onClick = { onViewEvent(RegisterViewEvents.RegisterClicked) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.register_submit))
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = { onViewEvent(RegisterViewEvents.GoToLoginClicked) },
            ) {
                Text(stringResource(R.string.register_go_to_login))
            }
        }
    }
}

@Preview
@Composable
private fun RegisterPreviewDark() {
    PawsitiveTheme(darkTheme = true) {
        RegisterPreview()
    }
}

@Preview
@Composable
private fun RegisterPreviewLight() {
    PawsitiveTheme(darkTheme = false) {
        RegisterPreview()
    }
}

@Composable
private fun RegisterPreview() {
    val nameState = RegisterState.Name("", RegisterState.Name.Validation.Valid)
    val emailState = RegisterState.Email("", RegisterState.Email.Validation.Valid)
    val passwordState = RegisterState.Password("", RegisterState.Password.Validation.Valid)
    val confirmPasswordState = RegisterState.ConfirmPassword("", true)
    val accountRoleMenuState = RegisterState.AccountRoleMenu(false, null, true)

    RegisterView(
        nameState = nameState,
        emailState = emailState,
        passwordState = passwordState,
        confirmPasswordState = confirmPasswordState,
        accountRoleMenuState = accountRoleMenuState,
        onViewEvent = { }
    )
}