package com.nastena.pawsitive.legacy_ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.datastores.AuthDataStore

class HomeViewModelFactory(
    private val authDataStore: AuthDataStore
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(authDataStore) as T
    }
}