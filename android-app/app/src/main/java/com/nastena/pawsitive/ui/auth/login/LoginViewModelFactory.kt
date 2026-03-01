package com.nastena.pawsitive.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.data.datastore.TokenManager
import com.nastena.pawsitive.data.repository.AuthRepository

class LoginViewModelFactory(
    private val repository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository, tokenManager) as T
    }
}