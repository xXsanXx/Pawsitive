package com.nastena.pawsitive.ui.screens.register

import android.util.Patterns
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.Navigation.To.PopUpType.Route
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.common.validation.ValidationState
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern
import kotlin.reflect.KClass

class RegisterViewModel(
    mainViewModel: MainViewModel,
    private val _accountRepository: AccountRepository
) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.Register::class

    companion object {
        private val NAME_REGEX = Pattern.compile("^[А-Яа-я\\s]{2,500}$")
    }

    private val _nameState = MutableStateFlow(
        RegisterState.Name(
            text = "", validation = ValidationState.Valid
        )
    )
    val nameState: StateFlow<RegisterState.Name> = _nameState.asStateFlow()
    private val _emailState = MutableStateFlow(
        RegisterState.Email(
            text = "", validation = ValidationState.Valid
        )
    )
    val emailState: StateFlow<RegisterState.Email> = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow(
        RegisterState.Password(
            "",
            validation = ValidationState.Valid,
            isVisible = true
        )
    )
    val passwordState: StateFlow<RegisterState.Password> = _passwordState.asStateFlow()

    private val _confirmPasswordState =
        MutableStateFlow(RegisterState.ConfirmPassword("", true, isVisible = true))
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

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        mainViewModel.hideNavigationBar()

        _nameState.update { it.copy(text = "", validation = ValidationState.Valid) }
        _emailState.update { it.copy(text = "", validation = ValidationState.Valid) }
        _passwordState.update {
            it.copy(
                text = "",
                validation = ValidationState.Valid,
                isVisible = false
            )
        }
        _confirmPasswordState.update { it.copy(text = "", isValid = true, isVisible = false) }
        _accountRoleMenuState.update {
            it.copy(
                isExpended = false,
                selected = null,
                isValid = true
            )
        }
    }

    fun onViewEvent(event: RegisterEvents) {
        when (event) {
            is RegisterEvents.Name.TextUpdated ->
                _nameState.update { it.copy(text = event.newText) }

            is RegisterEvents.Email.TextUpdated -> {
                _emailState.update { currentEmailState -> currentEmailState.copy(text = event.newText) }
            }

            is RegisterEvents.Password.TextUpdated -> {
                _passwordState.update { it.copy(text = event.newText) }
            }

            is RegisterEvents.ConfirmPassword.TextUpdated -> {
                _confirmPasswordState.update { it.copy(text = event.newText) }
            }

            RegisterEvents.AccountRoleMenu.ClickedMenu -> {
                _accountRoleMenuState.update { it.copy(isExpended = !it.isExpended) }
            }

            RegisterEvents.AccountRoleMenu.MenuDismissed -> {
                _accountRoleMenuState.update { it.copy(isExpended = false) }
            }

            is RegisterEvents.AccountRoleMenu.ClickedSelection -> {
                _accountRoleMenuState.update {
                    it.copy(
                        isExpended = false,
                        selected = event.accountRole
                    )
                }
            }

            RegisterEvents.GoToLoginClicked -> {
                mainViewModel.navigate(
                    To(
                        NavigationRoute.Login,
                        Route(NavigationRoute.Register::class)
                    )
                )
            }

            RegisterEvents.RegisterClicked -> {
                register()
            }

            RegisterEvents.Password.EyeClicked -> {
                _passwordState.update { it.copy(isVisible = !it.isVisible) }
            }

            RegisterEvents.ConfirmPassword.EyeClicked -> {
                _confirmPasswordState.update { it.copy(isVisible = !it.isVisible) }
            }
        }
    }

    private fun register() {

        val trimmedName = _nameState.value.text.trim()
        if (trimmedName.isBlank()) {
            _nameState.update { it.copy(validation = ValidationState.Empty) }
        } else if (
            trimmedName.length < 2 ||
            trimmedName.length > 500 ||
            !NAME_REGEX.matcher(trimmedName).matches()
        ) {
            _nameState.update { it.copy(validation = ValidationState.InvalidFormat) }
        } else {
            _nameState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }

        val trimmedEmail = _emailState.value.text.trim()
        if (trimmedEmail.isBlank()) {
            _emailState.update { it.copy(validation = ValidationState.Empty) }
        } else if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            _emailState.update { it.copy(validation = ValidationState.InvalidFormat) }
        } else {
            _emailState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }

        val trimmedPassword = _passwordState.value.text.trim()
        if (trimmedPassword.isBlank()) {
            _passwordState.update { it.copy(validation = ValidationState.Empty) }
        } else if (
            trimmedPassword.length < 12 ||
            !trimmedPassword.any { symbol -> symbol.isDigit() } ||
            !trimmedPassword.any { symbol -> symbol.isUpperCase() }
        ) {
            _passwordState.update { it.copy(validation = ValidationState.InvalidFormat) }
        } else {
            _passwordState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }

        val trimmedConfirmPassword = _confirmPasswordState.value.text.trim()
        _confirmPasswordState.update {
            it.copy(isValid = trimmedPassword == trimmedConfirmPassword)
        }

        _accountRoleMenuState.update { it.copy(isValid = it.selected != null) }

        val isAllValid = _nameState.value.validation is ValidationState.Valid &&
                _emailState.value.validation is ValidationState.Valid &&
                _passwordState.value.validation is ValidationState.Valid &&
                _confirmPasswordState.value.isValid &&
                _accountRoleMenuState.value.isValid

        if (isAllValid) {
            launchSave(
                operation = {
                    _accountRepository.register(
                        trimmedName,
                        trimmedEmail,
                        trimmedPassword,
                        _accountRoleMenuState.value.selected!!
                    )
                },
                onSuccess = {
                    mainViewModel.navigate(
                        To(
                            NavigationRoute.Login,
                            Route(NavigationRoute.Register::class)
                        )
                    )
                }
            )
        }


    }


}