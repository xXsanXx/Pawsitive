package com.nastena.pawsitive.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.main.MainViewModel

class SplashViewModelFactory(
    private val _accountRepository: AccountRepository,
    private val _mainViewModel: MainViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(_accountRepository, _mainViewModel) as T
    }
}