package com.nastena.pawsitive.ui.screens.user.profile

import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.dto.UserProfileResponse
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.common.Navigation.To
import com.nastena.pawsitive.ui.common.Navigation.To.PopUpType.Route
import com.nastena.pawsitive.ui.common.NavigationRoutes
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserProfileViewModel(
    mainViewModel: MainViewModel,
    private val _userRepository: UserRepository
) : BaseScreenViewModel(mainViewModel) {

    private val _state = MutableStateFlow(UserProfileState(name = "", email = ""))
    val state: StateFlow<UserProfileState> = _state.asStateFlow()

    override fun onEnter() {
        super.onEnter()

        mainViewModel.hideNavigationBar()

        launchSave(
            operation = { _userRepository.getUserProfileData() },
            onSuccess = { userProfile: UserProfileResponse ->
                _state.update { it.copy(name = userProfile.name, email = userProfile.email) }
            }
        )
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