package com.nastena.pawsitive.ui.screens.user.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.main.MainViewModel

class UserProfileViewModelFactory(
    private val _mainViewModel: MainViewModel,
    private val _userRepository: UserRepository,
    private val _accountRepository: AccountRepository,
    private val _filesRepository: FilesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserProfileViewModel(
            _mainViewModel,
            _userRepository,
            _accountRepository,
            _filesRepository
        ) as T
    }
}