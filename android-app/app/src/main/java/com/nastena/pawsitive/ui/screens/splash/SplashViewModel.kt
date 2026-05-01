package com.nastena.pawsitive.ui.screens.splash

import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation
import com.nastena.pawsitive.ui.common.navigation.NavigationBars
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlin.reflect.KClass

class SplashViewModel(
    private val _accountRepository: AccountRepository,
    mainViewModel: MainViewModel
) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.Splash::class

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        mainViewModel.hideNavigationBar()

        launchSave(
            operation = { _accountRepository.getAuthorizedRole() },
            onSuccess = { role: AccountRole? ->
                if (role == null) {
                    mainViewModel.navigate(
                        Navigation.To(NavigationRoute.Login, Navigation.To.PopUpType.Origin)
                    )
                } else {
                    mainViewModel.initializeNavigationBarSettings(
                        NavigationBars.fromAccountRole(
                            role
                        )
                    )
                }
            }
        )
    }
}