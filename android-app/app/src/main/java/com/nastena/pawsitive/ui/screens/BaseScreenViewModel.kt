package com.nastena.pawsitive.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.main.MainViewModel
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

abstract class BaseScreenViewModel(
    protected val mainViewModel: MainViewModel
) : ViewModel() {

    protected abstract val expectedRouteType: KClass<*>

    open fun onEnter(route: NavigationRoute) {
        if (!expectedRouteType.isInstance(route)) {
            throw IllegalArgumentException("Expected $expectedRouteType, got ${route::class.simpleName}")
        }

        Log.i("BaseScreenViewModel", "Entering screen ${this.javaClass.simpleName} with parameters: $route")
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