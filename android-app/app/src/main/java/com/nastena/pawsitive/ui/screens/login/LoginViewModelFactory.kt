package com.nastena.pawsitive.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.main.MainViewModel

class LoginViewModelFactory(
    private val _mainViewModel: MainViewModel,
    private val _accountRepository: AccountRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(_mainViewModel, _accountRepository) as T
    }
}