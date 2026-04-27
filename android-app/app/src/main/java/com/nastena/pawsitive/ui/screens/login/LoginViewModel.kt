package com.nastena.pawsitive.ui.screens.login

import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.Navigation.To.PopUpType.Route
import com.nastena.pawsitive.ui.common.navigation.NavigationBars
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.common.validation.ValidationState
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

class LoginViewModel(
    mainViewModel: MainViewModel,
    private val _accountRepository: AccountRepository
) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.Login::class

    private val _emailState = MutableStateFlow(
        LoginState.Email(
            text = "", validation = ValidationState.Valid
        )
    )
    val emailState: StateFlow<LoginState.Email> = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(
        LoginState.Password(
            "",
            validation = ValidationState.Valid,
            isVisible = true
        )
    )
    val passwordState: StateFlow<LoginState.Password> = _passwordState.asStateFlow()

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        mainViewModel.hideNavigationBar()

        _emailState.update { it.copy(text = "", validation = ValidationState.Valid) }
        _passwordState.update {
            it.copy(
                text = "",
                validation = ValidationState.Valid,
                isVisible = false
            )
        }
    }

    internal fun onViewEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.Email.TextUpdated -> {
                _emailState.update { currentEmailState -> currentEmailState.copy(text = event.newText) }
            }

            is LoginEvents.Password.TextUpdated -> {
                _passwordState.update { it.copy(text = event.newText) }
            }

            LoginEvents.Password.EyeClicked -> {
                _passwordState.update { it.copy(isVisible = !it.isVisible) }
            }

            LoginEvents.GoToRegistrationClicked -> {
                mainViewModel.navigate(
                    To(
                        NavigationRoute.Register,
                        Route(NavigationRoute.Login::class)
                    )
                )
            }

            LoginEvents.LoginClicked -> {
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

        val trimmedEmail = _emailState.value.text.trim()
        val trimmedPassword = _passwordState.value.text.trim()

        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS
            .matcher(trimmedEmail)
            .matches()

        val isPasswordValid = trimmedPassword.length >= 12

        _emailState.update {
            it.copy(
                validation = if (isEmailValid)
                    ValidationState.Valid
                else
                    ValidationState.InvalidFormat
            )
        }

        _passwordState.update {
            it.copy(
                validation = if (isPasswordValid)
                    ValidationState.Valid
                else
                    ValidationState.InvalidFormat
            )
        }

        if (!isEmailValid || !isPasswordValid) return

        launchSave(
            operation = {
                _accountRepository.login(trimmedEmail, trimmedPassword)
            },
            onSuccess = { role ->
                mainViewModel.initializeNavigationBarSettings(
                    NavigationBars.fromAccountRole(role)
                )

                mainViewModel.navigate(
                    Navigation.To(
                        NavigationRoute.fromAccountRole(role),
                        Navigation.To.PopUpType.Route(NavigationRoute.Login::class)
                    )
                )
            }
        )
    }
}