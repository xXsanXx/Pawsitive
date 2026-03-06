package com.nastena.pawsitive.ui.screens.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.main.MainUiEvents
import com.nastena.pawsitive.ui.main.MainUiEvents.Navigation.To.PopUpType.*
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.main.NavigationRoutes
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import com.nastena.pawsitive.ui.screens.login.LoginTextFieldState
import com.nastena.pawsitive.ui.screens.login.LoginViewEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    mainViewModel: MainViewModel,
    private val _accountRepository: AccountRepository,
) : BaseScreenViewModel(mainViewModel){

    private val _emailFieldState = MutableStateFlow(
        RegisterTextFieldState.Email("", isValid = true)
    )
    internal val emailFieldState: StateFlow<RegisterTextFieldState.Email> =
        _emailFieldState.asStateFlow()

    private val _passwordFieldState = MutableStateFlow(
        RegisterTextFieldState.Password("", isValid = true, isVisible = false)
    )
    internal val passwordFieldState: StateFlow<RegisterTextFieldState.Password> =
        _passwordFieldState.asStateFlow()

    private val _roleState = MutableStateFlow(
        AccountRole.USER
    )
    val roleState: StateFlow<AccountRole> = _roleState.asStateFlow()

    override fun onEnter() {
        super.onEnter()

        _emailFieldState.update { it.copy(text = "", isValid = true) }
        _passwordFieldState.update { it.copy(text = "", isValid = true, isVisible = false) }
    }

    internal fun onViewEvent(event: RegisterViewEvents) {
        when (event) {
            is RegisterViewEvents.Email.TextUpdated -> {
                updateTextField(event.newText, _emailFieldState)
            }

            is RegisterViewEvents.Password.TextUpdate -> {
                updateTextField(event.newText, _passwordFieldState)
            }

            RegisterViewEvents.Password.EyeClicked -> {
                _passwordFieldState.update { it.copy(isVisible = !it.isVisible) }
            }

            RegisterViewEvents.GoToLoginClicked -> {
                mainViewModel.navigateTo(
                    NavigationRoutes.LOGIN,
                )
            }

            RegisterViewEvents.RegisterClicked -> {
                regiter()
            }
        }
    }

    private inline fun <reified T : RegisterTextFieldState> updateTextField(
        newText: String,
        textFieldState: MutableStateFlow<T>
    ) {
        var isValid = textFieldState.value.isValid
        if (!newText.isBlank()) {
            isValid = true
        }
        textFieldState.update { it.copy(text = newText, isValid = isValid) as T }
    }

    fun register() {
        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()

        if (trimmedEmail.isBlank() || trimmedPassword.isBlank()) {
            _state.value = RegisterState.Error(RegisterError.EmptyFields)
            return
        }

        if (!isValidEmail(trimmedEmail)) {
            _state.value = RegisterState.Error(RegisterError.InvalidEmail)
            return
        }

        if (!isValidPassword(trimmedPassword)) {
            _state.value = RegisterState.Error(RegisterError.WeakPassword)
            return
        }

        if (role != AccountRole.USER && role != AccountRole.SHELTER) {
            _state.value = RegisterState.Error(RegisterError.InvalidRole)
            return
        }

        viewModelScope.launch {
            _state.value = RegisterState.Loading

            val result = repository.register(
                trimmedEmail,
                trimmedPassword,
                role
            )

            _state.value = result.fold(
                onSuccess = {
                    RegisterState.Success
                },
                onFailure = { throwable ->
                    handleServerError(throwable)
                }
            )
        }
    }

    private fun isValidEmail(email: String) : Boolean {
        return Patterns.EMAIL_ADDRESS
            .matcher(email)
            .matches()
    }

    private fun isValidPassword(password: String) : Boolean {
        if (password.length < 12) return false

        val hasDigit = password.any { it.isDigit() }

        val hasLetter = password.any { it.isLetter() }

        return hasDigit && hasLetter
    }

//    private fun handleServerError(throwable: Throwable): RegisterState {
//        val message = throwable.message ?: return RegisterState.Error(RegisterError.Unknown)
//
//        return when {
//            message.contains("409") ->
//                RegisterState.Error(RegisterError.EmailAlreadyExists)
//
//            message.contains("400") ->
//                RegisterState.Error(RegisterError.ServerError("Некорректные данные"))
//
//            else ->
//                RegisterState.Error(RegisterError.ServerError(message))
//        }
//    }



}
