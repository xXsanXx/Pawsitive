package com.nastena.pawsitive.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.common.ServerUnknownErrorCodeException
import com.nastena.pawsitive.ui.common.navigation.Navigation
import com.nastena.pawsitive.ui.common.navigation.NavigationBars
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _navigationEvents = MutableSharedFlow<Navigation>()
    internal val navigationEvents: SharedFlow<Navigation> =
        _navigationEvents.asSharedFlow()

    private val _mainState = MutableStateFlow<MainState>(MainState.Idle)
    internal val mainState: StateFlow<MainState> = _mainState.asStateFlow()

    private val _navigationBarState = MutableStateFlow(
        NavigationBarState(
            isVisible = false,
            settings = NavigationBars.EMPTY,
            selected = 0
        )
    )
    internal val navigationBarState: StateFlow<NavigationBarState> =
        _navigationBarState.asStateFlow()

    internal fun onViewEvent(viewEvent: MainViewEvents) {
        when (viewEvent) {
            MainViewEvents.ErrorBox.ClickedOk -> {
                if (_mainState.value is MainState.Error) {
                    _mainState.update { MainState.Idle }
                }
            }

            is MainViewEvents.NavigationBar.ClickedItem -> {
                _navigationBarState.update { it.copy(selected = viewEvent.index) }
            }

            MainViewEvents.MessageBox.ClickedOk -> {
                when (val currentState = _mainState.value) {
                    is MainState.Message -> {
                        _mainState.update { MainState.Idle }
                        currentState.onOkayCallback()
                    }

                    else -> {}
                }
            }
        }
    }

    fun navigate(
        navigation: Navigation
    ) {
        viewModelScope.launch {
            _navigationEvents.emit(navigation)
        }
    }

    fun showLoading() {
        _mainState.update { MainState.Loading }
    }

    fun showMessage(messageId: Int, onOkay: () -> Unit = { }) {
        _mainState.update { MainState.Message(messageId, onOkay) }
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
                    navigate(
                        Navigation.To(
                            NavigationRoute.Login,
                            Navigation.To.PopUpType.Origin
                        )
                    )
                }
            }
        }

        _mainState.update { MainState.Error(throwable) }
    }


    fun hideNavigationBar() {
        _navigationBarState.update { it.copy(isVisible = false) }
    }

    fun initializeNavigationBarSettings(settings: NavigationBars.Settings) {
        _navigationBarState.update {
            it.copy(isVisible = true, settings = settings, selected = settings.initialSelected)
        }
        val initialItem: NavigationBars.Item = settings.items[settings.initialSelected]
        viewModelScope.launch {
            _navigationEvents.emit(initialItem.navigation)
        }
    }
}