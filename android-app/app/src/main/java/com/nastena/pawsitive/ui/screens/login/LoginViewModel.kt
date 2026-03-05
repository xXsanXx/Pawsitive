package com.nastena.pawsitive.ui.screens.login

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.main.MainUiEvents
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.main.NavigationRoutes
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    mainViewModel: MainViewModel,
    private val _accountRepository: AccountRepository
) : BaseScreenViewModel(mainViewModel) {

    private val _emailFieldState = MutableStateFlow(
        LoginTextFieldState.Email("", isValid = true)
    )
    internal val emailFieldState: StateFlow<LoginTextFieldState.Email> =
        _emailFieldState.asStateFlow()

    private val _passwordFieldState = MutableStateFlow(
        LoginTextFieldState.Password("", isValid = true, isVisible = false)
    )
    internal val passwordFieldState: StateFlow<LoginTextFieldState.Password> =
        _passwordFieldState.asStateFlow()

    override fun onEnter() {
        super.onEnter()

        _emailFieldState.update { it.copy(text = "", isValid = true) }
        _passwordFieldState.update { it.copy(text = "", isValid = true, isVisible = false) }
    }

    internal fun onViewEvent(event: LoginViewEvents) {
        when (event) {
            is LoginViewEvents.Email.TextUpdated -> {
                updateTextField(event.newText, _emailFieldState)
            }

            is LoginViewEvents.Password.TextUpdate -> {
                updateTextField(event.newText, _passwordFieldState)
            }

            LoginViewEvents.Password.EyeClicked -> {
                _passwordFieldState.update { it.copy(isVisible = !it.isVisible) }
            }

            LoginViewEvents.GoToRegistrationClicked -> {
                mainViewModel.navigateTo(
                    NavigationRoutes.REGISTER,
                    popUpType = MainUiEvents.Navigation.To.PopUpType.Route(NavigationRoutes.LOGIN)
                )
            }

            LoginViewEvents.LoginClicked -> {
                login()
            }
        }
    }

    private inline fun <reified T : LoginTextFieldState> updateTextField(
        newText: String,
        textFieldState: MutableStateFlow<T>
    ) {
        var isValid = textFieldState.value.isValid
        if (!newText.isBlank()) {
            isValid = true
        }
        textFieldState.update { it.copy(text = newText, isValid = isValid) as T }
    }

    private fun login() {
        val trimmedEmail = _emailFieldState.value.text.trim()
        val isValidEmail = !trimmedEmail.isBlank()
        _emailFieldState.update { it.copy(isValid = isValidEmail) }

        val trimmedPassword = _passwordFieldState.value.text.trim()
        val isValidPassword = !trimmedPassword.isBlank()
        _passwordFieldState.update { it.copy(isValid = isValidPassword) }

        if (!isValidEmail || !isValidPassword) {
            return;
        }

        launchSave(
            operation = {
                _accountRepository.login(trimmedEmail, trimmedPassword)
            },
            onSuccess = { role: AccountRole ->
                mainViewModel.navigateTo(
                    NavigationRoutes.fromAccountRole(role),
                    popUpType = MainUiEvents.Navigation.To.PopUpType.Route(NavigationRoutes.LOGIN)
                )
            }
        )
    }
}