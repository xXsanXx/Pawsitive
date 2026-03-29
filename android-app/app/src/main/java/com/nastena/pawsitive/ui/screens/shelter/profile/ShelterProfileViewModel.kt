package com.nastena.pawsitive.ui.screens.shelter.profile

import android.util.Log
import com.nastena.pawsitive.dto.ShelterProfileResponse
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.NavigationRoutes
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ShelterProfileViewModel(
    mainViewModel: MainViewModel,
    private val _shelterRepository: ShelterRepository,
    private val _accountRepository: AccountRepository
) : BaseScreenViewModel(mainViewModel) {

    private val _emailState = MutableStateFlow("")
    val emailState: StateFlow<String> = _emailState.asStateFlow()

    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> = _nameState.asStateFlow()

    private val _addressState = MutableStateFlow("")
    val addressState: StateFlow<String> = _addressState.asStateFlow()

    private val _infoState = MutableStateFlow("")
    val infoState: StateFlow<String> = _infoState.asStateFlow()

    private val _phoneState = MutableStateFlow("")
    val phoneState: StateFlow<String> = _phoneState.asStateFlow()


    override fun onEnter() {
        super.onEnter()

        launchSave(
            operation = {
                Log.d("ShelterProfile", "Loading profile data")
                _shelterRepository.getShelterProfileData()
            },

            onSuccess = { shelterProfile: ShelterProfileResponse ->
                Log.d("ShelterProfile", "Success: $shelterProfile")
                _emailState.update { shelterProfile.email }
                _nameState.update { shelterProfile.name }
                _addressState.update { shelterProfile.address }
                _infoState.update { shelterProfile.info }
                _phoneState.update { shelterProfile.phone }
            }
        )
    }

    fun onViewEvent(event: ShelterProfileEvents) {
        when (event) {
            ShelterProfileEvents.EditingClicked -> {
                mainViewModel.navigate(
                    To(
                        NavigationRoutes.SHELTER_PROFILE_EDITING
                    ),
                )
            }

            ShelterProfileEvents.LogoutClicked -> onLogoutClicked()
        }
    }

    fun onLogoutClicked() {
        launchSave(
            operation = { _accountRepository.logout() },
            onSuccess = {
                mainViewModel.navigate(
                    To(
                        NavigationRoutes.LOGIN,
                        To.PopUpType.Origin
                    )
                )
            }
        )
    }

}
