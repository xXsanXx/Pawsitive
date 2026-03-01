package com.nastena.pawsitive.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.data.repository.AuthRepository
import com.nastena.pawsitive.ui.auth.register.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun register(email: String, password: String, role: String) {
        viewModelScope.launch {
            _state.value = RegisterState.Loading

            val result = repository.register(email, password, role)

            _state.value = result.fold(
                onSuccess = {RegisterState.Success},
                onFailure = {RegisterState.Error(it.message ?: "Unknown error") }
            )
        }
    }
}
