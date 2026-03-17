package com.nastena.pawsitive.ui.screens.user.profile

import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.ui.common.Navigation
import com.nastena.pawsitive.ui.common.Navigation.*
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


    private val _state = MutableStateFlow(UserProfileState())
    val state: StateFlow<UserProfileState> = _state.asStateFlow()

    override fun onEnter() {
        super.onEnter()

        mainViewModel.hideNavigationBar()

//        val currentUser = accountRepository.getCurrentAccount()
//
//        _state.update {
//            it.copy(
//                email = currentUser.email,
//                name = currentUser.name ?: "",
//                description = currentUser.description ?: "",
//                isEditing = false
//            )
//        }
    }

    fun onViewEvent(event: UserProfileViewEvents) {
        when (event) {
            UserProfileViewEvents.EditClicked -> {
                _state.update { it.copy(isEditing = true) }
            }

            is UserProfileViewEvents.DescriptionChanged -> {
                _state.update { it.copy(description = event.value) }
            }

            is UserProfileViewEvents.NameChanged -> {
                _state.update { it.copy(name = event.value) }
            }


            UserProfileViewEvents.LogoutClicked ->
                mainViewModel.navigate(
                    To(
                        NavigationRoutes.LOGIN,
                        Navigation.To.PopUpType.Origin
                    )
                )

            UserProfileViewEvents.SaveClicked -> TODO()


        }
    }


}