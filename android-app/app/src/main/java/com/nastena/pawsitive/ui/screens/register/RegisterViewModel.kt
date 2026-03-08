package com.nastena.pawsitive.ui.screens.register

import android.util.Patterns
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.common.Navigation
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.common.NavigationRoutes
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel(
    mainViewModel: MainViewModel,
    private val _accountRepository: AccountRepository
) : BaseScreenViewModel(mainViewModel) {

    private val _emailState = MutableStateFlow(
        RegisterState.Email(
            text = "", validation = RegisterState.Email.Validation.Valid
        )
    )
    val emailState: StateFlow<RegisterState.Email> = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(
        RegisterState.Password(
            "",
            validation = RegisterState.Password.Validation.Valid
        )
    )
    val passwordState: StateFlow<RegisterState.Password> = _passwordState.asStateFlow()

    private val _confirmPasswordState = MutableStateFlow(RegisterState.ConfirmPassword("", true))
    val confirmPasswordState: StateFlow<RegisterState.ConfirmPassword> =
        _confirmPasswordState.asStateFlow()

    private val _accountRoleMenuState = MutableStateFlow(
        RegisterState.AccountRoleMenu(
            isExpended = false,
            selected = null,
            isValid = true
        )
    )
    val accountRoleMenuState: StateFlow<RegisterState.AccountRoleMenu> =
        _accountRoleMenuState.asStateFlow()

    override fun onEnter() {
        super.onEnter()

        mainViewModel.hideNavigationBar()

        _emailState.update { it.copy(text = "", validation = RegisterState.Email.Validation.Valid) }
        _passwordState.update {
            it.copy(
                text = "",
                validation = RegisterState.Password.Validation.Valid
            )
        }
        _confirmPasswordState.update { it.copy(text = "", isValid = true) }
        _accountRoleMenuState.update {
            it.copy(
                isExpended = false,
                selected = null,
                isValid = true
            )
        }
    }

    fun onViewEvent(event: RegisterViewEvents) {
        when (event) {
            is RegisterViewEvents.Email.TextUpdated -> {
                _emailState.update { currentEmailState -> currentEmailState.copy(text = event.newText) }
            }

            is RegisterViewEvents.Password.TextUpdated -> {
                _passwordState.update { it.copy(text = event.newText) }
            }

            is RegisterViewEvents.ConfirmPassword.TextUpdated -> {
                _confirmPasswordState.update { it.copy(text = event.newText) }
            }

            RegisterViewEvents.AccountRoleMenu.ClickedMenu -> {
                _accountRoleMenuState.update { it.copy(isExpended = !it.isExpended) }
            }

            RegisterViewEvents.AccountRoleMenu.MenuDismissed -> {
                _accountRoleMenuState.update { it.copy(isExpended = false) }
            }

            is RegisterViewEvents.AccountRoleMenu.ClickedSelection -> {
                _accountRoleMenuState.update {
                    it.copy(
                        isExpended = false,
                        selected = event.accountRole
                    )
                }
            }

            RegisterViewEvents.GoToLoginClicked -> {
                mainViewModel.navigate(
                    Navigation.To(
                        NavigationRoutes.LOGIN,
                        Navigation.To.PopUpType.Route(NavigationRoutes.REGISTER)
                    )
                )
            }

            RegisterViewEvents.RegisterClicked -> {
                register()
            }

        }
    }

    private fun register() {

        val trimmedEmail = _emailState.value.text.trim()
        if (trimmedEmail.isBlank()) {
            _emailState.update { it.copy(validation = RegisterState.Email.Validation.Empty) }
        } else if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            _emailState.update { it.copy(validation = RegisterState.Email.Validation.InvalidFormat) }
        } else {
            _emailState.update {
                it.copy(validation = RegisterState.Email.Validation.Valid)
            }
        }

        val trimmedPassword = _passwordState.value.text.trim()
        if (trimmedPassword.isBlank()) {
            _passwordState.update { it.copy(validation = RegisterState.Password.Validation.Empty) }
        } else if (
            trimmedPassword.length < 12 ||
            !trimmedPassword.any { symbol -> symbol.isDigit() } ||
            !trimmedPassword.any { symbol -> symbol.isUpperCase() }
        ) {
            _passwordState.update { it.copy(validation = RegisterState.Password.Validation.InvalidFormat) }
        } else {
            _passwordState.update {
                it.copy(validation = RegisterState.Password.Validation.Valid)
            }
        }

        val trimmedConfirmPassword = _confirmPasswordState.value.text.trim()
        _confirmPasswordState.update {
            it.copy(isValid = trimmedPassword == trimmedConfirmPassword)
        }

        _accountRoleMenuState.update { it.copy(isValid = it.selected != null) }

        val isAllValid = _emailState.value.validation is RegisterState.Email.Validation.Valid &&
                _passwordState.value.validation is RegisterState.Password.Validation.Valid &&
                _confirmPasswordState.value.isValid &&
                _accountRoleMenuState.value.isValid

        if (isAllValid) {
            launchSave(
                operation = {
                    _accountRepository.register(
                        trimmedEmail,
                        trimmedPassword,
                        _accountRoleMenuState.value.selected!!
                    )
                },
                onSuccess = {
                    mainViewModel.navigate(
                        Navigation.To(
                            NavigationRoutes.LOGIN,
                            Navigation.To.PopUpType.Route(NavigationRoutes.REGISTER)
                        )
                    )
                }
            )
        }


    }
}