package com.nastena.pawsitive.legacy_ui.user.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.AnimalRepository

class UserHomeViewModelFactory(
    private val repository: AnimalRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserHomeViewModel(repository) as T
    }
}