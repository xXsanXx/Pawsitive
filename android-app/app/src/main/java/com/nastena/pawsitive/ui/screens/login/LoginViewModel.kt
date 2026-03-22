package com.nastena.pawsitive.ui.screens.login

import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation
import com.nastena.pawsitive.ui.common.navigation.NavigationBars
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.common.navigation.NavigationRoutes
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

        mainViewModel.hideNavigationBar()

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
                mainViewModel.navigate(
                    Navigation.To(
                        NavigationRoutes.REGISTER,
                        Navigation.To.PopUpType.Route(NavigationRoutes.LOGIN)
                    )
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
                mainViewModel.initializeNavigationBarSettings(
                    NavigationBars.fromAccountRole(
                        role
                    )
                )
                mainViewModel.navigate(
                    Navigation.To(
                        NavigationRoutes.fromAccountRole(role),
                        Navigation.To.PopUpType.Route(NavigationRoutes.LOGIN)
                    )
                )
            }
        )
    }
}