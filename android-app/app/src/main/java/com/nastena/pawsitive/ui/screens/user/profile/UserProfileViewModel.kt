package com.nastena.pawsitive.ui.screens.user.profile

import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.common.Navigation.To
import com.nastena.pawsitive.ui.common.Navigation.To.PopUpType.Route
import com.nastena.pawsitive.ui.common.NavigationRoutes
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserProfileViewModel(
    mainViewModel: MainViewModel,
    private val accountRepository: AccountRepository
) : BaseScreenViewModel(mainViewModel) {

    private val _state = MutableStateFlow(UserProfileState.Screen(name = "", email = ""))
    val state: StateFlow<UserProfileState.Screen> = _state.asStateFlow()

    override fun onEnter() {
        super.onEnter()

        mainViewModel.hideNavigationBar()

        _state.update { UserProfileState.Screen(name = "", email = "") }
    }

    fun onViewEvent(event: UserProfileViewEvents) {
        when (event) {
            UserProfileViewEvents.LogoutClicked ->
                mainViewModel.navigate(
                    To(
                        NavigationRoutes.LOGIN,
                        Route(NavigationRoutes.USER_HOME)
                    )
                )
        }
    }


}