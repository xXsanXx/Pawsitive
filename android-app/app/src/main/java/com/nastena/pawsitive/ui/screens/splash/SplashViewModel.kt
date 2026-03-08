package com.nastena.pawsitive.ui.screens.splash

import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.common.ServerParsedException
import com.nastena.pawsitive.common.ServerUnknownErrorCodeException
import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.dto.ErrorCode
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.common.Navigation
import com.nastena.pawsitive.ui.common.NavigationBars
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.common.NavigationRoutes
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.launch

class SplashViewModel(
    private val _accountRepository: AccountRepository,
    mainViewModel: MainViewModel
) : BaseScreenViewModel(mainViewModel) {

    override fun onEnter() {
        super.onEnter()

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
                                        NavigationRoutes.LOGIN,
                                        Navigation.To.PopUpType.Route(
                                            NavigationRoutes.SPLASH
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
                                        NavigationRoutes.LOGIN,
                                        Navigation.To.PopUpType.Route(
                                            NavigationRoutes.SPLASH
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