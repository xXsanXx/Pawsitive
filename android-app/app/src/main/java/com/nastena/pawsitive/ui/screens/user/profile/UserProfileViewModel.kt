package com.nastena.pawsitive.ui.screens.user.profile

import android.util.Log
import com.nastena.pawsitive.dto.UserProfileResponse
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.common.Navigation
import com.nastena.pawsitive.ui.common.Navigation.*
import com.nastena.pawsitive.ui.common.Navigation.To.PopUpType.*
import com.nastena.pawsitive.ui.common.NavigationRoutes
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import com.nastena.pawsitive.ui.screens.register.RegisterViewEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserProfileViewModel(
    mainViewModel: MainViewModel,
    private val _userRepository: UserRepository,
    private val _accountRepository: AccountRepository
) : BaseScreenViewModel(mainViewModel) {

    private val _state = MutableStateFlow(UserProfileState(name = "", email = ""))
    val state: StateFlow<UserProfileState> = _state.asStateFlow()

    override fun onEnter() {
        super.onEnter()

        mainViewModel.hideNavigationBar()

        launchSave(
            operation = {
                Log.d("UserProfile", "Loading profile data")
                _userRepository.getUserProfileData() },

            onSuccess = { userProfile: UserProfileResponse ->
                Log.d("UserProfile", "Success: $userProfile")
                _state.update { it.copy(name = userProfile.name, email = userProfile.email) }
            }
        )
    }

    fun onViewEvent(event: UserProfileViewEvents) {
        when (event) {
            UserProfileViewEvents.LogoutClicked -> onLogoutClicked()
        }
    }

    fun onLogoutClicked() {
        launchSave(
            operation = { _accountRepository.logout() },
            onSuccess = {
                mainViewModel.navigate(
                    To(
                        NavigationRoutes.LOGIN,
                        Route(NavigationRoutes.USER_HOME)
                    )
                )
            }
        )
    }
}
