package com.nastena.pawsitive.ui.screens.user.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.register.RegisterViewModel

class UserProfileViewModelFactory(
    private val _mainViewModel: MainViewModel,
    private val _accountRepository: AccountRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserProfileViewModel(_mainViewModel, _accountRepository) as T
    }
}