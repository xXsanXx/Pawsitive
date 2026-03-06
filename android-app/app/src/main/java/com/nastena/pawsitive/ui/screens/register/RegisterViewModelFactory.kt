package com.nastena.pawsitive.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.main.MainViewModel

class RegisterViewModelFactory(
    private val _mainViewModel: MainViewModel,
    private val _accountRepository: AccountRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(_mainViewModel, _accountRepository) as T
    }
}