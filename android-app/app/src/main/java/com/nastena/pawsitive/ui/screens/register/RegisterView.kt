package com.nastena.pawsitive.ui.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
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
import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.ui.common.PawsitiveTextButton
import com.nastena.pawsitive.ui.common.validation.ValidationState

@Composable
fun RegisterView(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel
) {

    val nameState by viewModel.nameState.collectAsState()
    val emailState by viewModel.emailState.collectAsState()
    val passwordState by viewModel.passwordState.collectAsState()
    val confirmPasswordState by viewModel.confirmPasswordState.collectAsState()
    val roleState by viewModel.accountRoleMenuState.collectAsState()

    RegisterView(
        modifier = modifier,
        nameState = nameState,
        emailState = emailState,
        passwordState = passwordState,
        confirmPasswordState = confirmPasswordState,
        roleState = roleState,
        onEvent = { viewModel.onViewEvent(it) }
    )
}

@Composable
private fun RegisterView(
    modifier: Modifier = Modifier,
    nameState: RegisterState.Name,
    emailState: RegisterState.Email,
    passwordState: RegisterState.Password,
    confirmPasswordState: RegisterState.ConfirmPassword,
    roleState: RegisterState.AccountRoleMenu,
    onEvent: (RegisterEvents) -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
                .widthIn(max = 420.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(32.dp))

            Icon(
                imageVector = Icons.Default.Pets,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(72.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.register_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = nameState.text,
                onValueChange = {
                    onEvent(RegisterEvents.Name.TextUpdated(it))
                },
                label = { Text(stringResource(R.string.register_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null)
                },
                singleLine = true,
                isError = nameState.validation != ValidationState.Valid,
                supportingText = {
                    when (nameState.validation) {
                        ValidationState.Empty ->
                            Text(stringResource(R.string.register_name_is_empty))

                        ValidationState.InvalidFormat ->
                            Text(stringResource(R.string.register_name_invalid))

                        else -> {}
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = emailState.text,
                onValueChange = {
                    onEvent(RegisterEvents.Email.TextUpdated(it))
                },
                label = { Text(stringResource(R.string.register_email_label)) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = emailState.validation != ValidationState.Valid,
                supportingText = {
                    when (emailState.validation) {
                        ValidationState.Empty ->
                            Text(stringResource(R.string.not_empty))

                        ValidationState.InvalidFormat ->
                            Text(stringResource(R.string.register_email_invalid))

                        else -> {}
                    }
                }
            )

            Spacer(Modifier.height(16.dp))


            OutlinedTextField(
                value = passwordState.text,
                onValueChange = {
                    onEvent(RegisterEvents.Password.TextUpdated(it))
                },
                label = { Text(stringResource(R.string.register_password_label)) },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordState.isVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },

                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = passwordState.validation != ValidationState.Valid,
                supportingText = {
                    when (passwordState.validation) {
                        ValidationState.Empty ->
                            Text(stringResource(R.string.not_empty))

                        ValidationState.InvalidFormat ->
                            Text(stringResource(R.string.register_password_invalid))

                        else -> {}
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = { onEvent(RegisterEvents.Password.EyeClicked) }
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

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPasswordState.text,
                onValueChange = {
                    onEvent(RegisterEvents.ConfirmPassword.TextUpdated(it))
                },
                label = { Text(stringResource(R.string.register_confirm_password_label)) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

                visualTransformation =
                    if (confirmPasswordState.isVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },

                isError = !confirmPasswordState.isValid,

                supportingText = {
                    if (!confirmPasswordState.isValid) {
                        Text(stringResource(R.string.register_confirm_password_invalid))
                    }
                },

                trailingIcon = {
                    IconButton(
                        onClick = { onEvent(RegisterEvents.ConfirmPassword.EyeClicked) }
                    ) {
                        Icon(
                            imageVector =
                                if (confirmPasswordState.isVisible)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                }
            )

            Spacer(Modifier.height(24.dp))

            RoleSelector(roleState, onEvent)

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    onEvent(RegisterEvents.RegisterClicked)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(stringResource(R.string.register_submit))
            }

            Spacer(Modifier.height(12.dp))

            PawsitiveTextButton(
                onClick = { onEvent(RegisterEvents.GoToLoginClicked) }
            ) {
                Text(stringResource(R.string.register_go_to_login))
            }

        }
    }
}

@Composable
fun RoleSelector(
    roleState: RegisterState.AccountRoleMenu,
    onEvent: (RegisterEvents) -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = stringResource(R.string.register_choose_role_label),
            style = MaterialTheme.typography.labelLarge
        )

        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = roleState.selected == AccountRole.USER,
                onClick = {
                    onEvent(
                        RegisterEvents.AccountRoleMenu.ClickedSelection(AccountRole.USER)
                    )
                }
            )

            Text(stringResource(R.string.register_role_user))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = roleState.selected == AccountRole.SHELTER,
                onClick = {
                    onEvent(
                        RegisterEvents.AccountRoleMenu.ClickedSelection(AccountRole.SHELTER)
                    )
                }
            )

            Text(stringResource(R.string.register_role_shelter))
        }

        if (!roleState.isValid) {
            Spacer(Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.register_choose_role_invalid),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}