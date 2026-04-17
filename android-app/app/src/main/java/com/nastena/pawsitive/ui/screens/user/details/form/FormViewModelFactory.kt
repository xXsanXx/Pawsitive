package com.nastena.pawsitive.ui.screens.user.details.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.main.MainViewModel

class FormViewModelFactory(
    private val _mainViewModel: MainViewModel,
    private val _userRepository: UserRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FormViewModel(_mainViewModel, _userRepository) as T
    }
}
