package com.nastena.pawsitive.ui.screens.splash

import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.common.ServerParsedException
import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.dto.ErrorCode
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.main.NavigationRoutes
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import com.nastena.pawsitive.ui.main.MainState
import com.nastena.pawsitive.ui.main.MainUiEvents
import kotlinx.coroutines.launch

class SplashViewModel(
    private val _accountRepository: AccountRepository,
    mainViewModel: MainViewModel
) : BaseScreenViewModel(mainViewModel) {

    override fun onEnter() {
        super.onEnter()

        viewModelScope.launch {
            _accountRepository.getAuthorizedRole().fold(
                onSuccess = { role: AccountRole ->
                    mainViewModel.navigateTo(
                        NavigationRoutes.fromAccountRole(role),
                        popUpType = MainUiEvents.Navigation.To.PopUpType.Route(NavigationRoutes.SPLASH)
                    )
                },

                onFailure = { throwable ->
                    when (throwable) {
                        is ServerParsedException -> {
                            if (throwable.errorCode == ErrorCode.UNAUTHORIZED) {
                                mainViewModel.navigateTo(
                                    NavigationRoutes.LOGIN,
                                    popUpType = MainUiEvents.Navigation.To.PopUpType.Route(NavigationRoutes.SPLASH)
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