package com.nastena.pawsitive.ui.screens.splash

import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.common.ServerParsedException
import com.nastena.pawsitive.common.ServerUnknownErrorCodeException
import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.dto.ErrorCode
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation
import com.nastena.pawsitive.ui.common.navigation.NavigationBars
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class SplashViewModel(
    private val _accountRepository: AccountRepository,
    mainViewModel: MainViewModel
) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.Splash::class

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        mainViewModel.hideNavigationBar()

        viewModelScope.launch {
            _accountRepository.getAuthorizedRole().fold(
                onSuccess = { role: AccountRole ->
                    mainViewModel.initializeNavigationBarSettings(
                        NavigationBars.fromAccountRole(
                            role
                        )
                    )
                },

                onFailure = { throwable ->
                    when (throwable) {
                        is ServerParsedException -> {
                            if (throwable.errorCode == ErrorCode.UNAUTHORIZED) {
                                mainViewModel.navigate(
                                    Navigation.To(
                                        NavigationRoute.Login,
                                        Navigation.To.PopUpType.Route(
                                            NavigationRoute.Splash::class
                                        )
                                    )
                                )
                            } else {
                                mainViewModel.handleError(throwable)
                            }
                        }

                        is ServerUnknownErrorCodeException -> {
                            if (throwable.httpCode == 403) {
                                mainViewModel.navigate(
                                    Navigation.To(
                                        NavigationRoute.Login,
                                        Navigation.To.PopUpType.Route(
                                            NavigationRoute.Splash::class
                                        )
                                    )
                                )
                            } else {
                                mainViewModel.handleError(throwable)
                            }
                        }

                        else -> mainViewModel.handleError(throwable)
                    }
                }
            )
        }
    }
}