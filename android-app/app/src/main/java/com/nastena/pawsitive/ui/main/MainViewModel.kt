package com.nastena.pawsitive.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.common.ServerUnknownErrorCodeException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<MainUiEvents.Navigation>()
    internal val navigationEvents: SharedFlow<MainUiEvents.Navigation> =
        _navigationEvents.asSharedFlow()

    private val _mainState = MutableStateFlow<MainState>(MainState.Idle)
    internal val mainState: StateFlow<MainState> = _mainState.asStateFlow()

    internal fun onViewEvent(viewEvent: MainViewEvents) {
        when (viewEvent) {
            MainViewEvents.ErrorBox.ClickedOk -> {
                if (_mainState.value is MainState.Error) {
                    _mainState.update { MainState.Idle }
                }
            }
        }
    }

    fun navigateTo(
        route: String,
        popUpType: MainUiEvents.Navigation.To.PopUpType = MainUiEvents.Navigation.To.PopUpType.None
    ) {
        viewModelScope.launch {
            _navigationEvents.emit(MainUiEvents.Navigation.To(route, popUpType))
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvents.emit(MainUiEvents.Navigation.Back)
        }
    }

    fun showLoading() {
        _mainState.update { MainState.Loading }
    }

    fun hideLoading() {
        if (_mainState.value is MainState.Loading) {
            _mainState.update { MainState.Idle }
        }
    }

    fun handleError(throwable: Throwable) {
        Log.i("MainView", "Handling error", throwable)

        when (throwable) {
            is ServerUnknownErrorCodeException -> {
                if (throwable.httpCode == 403) {
                    navigateTo(
                        NavigationRoutes.LOGIN,
                        MainUiEvents.Navigation.To.PopUpType.Origin
                    )
                }
            }
        }

        _mainState.update { MainState.Error(throwable) }
    }
}