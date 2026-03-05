package com.nastena.pawsitive.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.main.MainState
import kotlinx.coroutines.launch

abstract class BaseScreenViewModel(
    protected val mainViewModel: MainViewModel
) : ViewModel() {

    open fun onEnter() {
    }

    fun <T> launchSave(
        operation: suspend () -> Result<T>,
        onSuccess: (T) -> Unit
    ) {
        viewModelScope.launch {
            mainViewModel.showLoading()

            operation().fold(
                onSuccess = { result ->
                    mainViewModel.hideLoading()
                    onSuccess(result)
                },
                onFailure = { throwable ->
                    mainViewModel.handleError(throwable)
                }
            )
        }
    }
}