package com.nastena.pawsitive.ui.screens.splash

import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation
import com.nastena.pawsitive.ui.common.navigation.NavigationBars
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

class SplashViewModel(
    private val _accountRepository: AccountRepository,
    mainViewModel: MainViewModel
) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.Splash::class

    private val _state = MutableStateFlow<SplashState>(SplashState.Loading)
    val state: StateFlow<SplashState> = _state.asStateFlow()

    private var _role: AccountRole? = null

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        _role?.let {
            mainViewModel.initializeNavigationBarSettings(
                NavigationBars.fromAccountRole(
                    it
                )
            )
            return
        }

        _state.update { SplashState.Loading }
        mainViewModel.hideNavigationBar()

        launchSave(
            operation = { _accountRepository.getAuthorizedRole() },
            onSuccess = { role: AccountRole? ->
                _role = role
                _state.update { SplashState.Ready }
            }
        )
    }

    fun onViewEvent(event: SplashEvents) {
        when (event) {
            SplashEvents.AnimationDone -> {
                _role?.let {
                    mainViewModel.initializeNavigationBarSettings(
                        NavigationBars.fromAccountRole(
                            it
                        )
                    )
                } ?: {
                    mainViewModel.navigate(
                        Navigation.To(NavigationRoute.Login, Navigation.To.PopUpType.Origin)
                    )
                }
            }
        }
    }
}