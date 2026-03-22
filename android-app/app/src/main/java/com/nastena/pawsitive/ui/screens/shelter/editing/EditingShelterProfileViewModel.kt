package com.nastena.pawsitive.ui.screens.shelter.editing

import android.util.Log
import android.util.Patterns
import com.nastena.pawsitive.dto.ShelterProfileResponse
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.Navigation.To.PopUpType.Route
import com.nastena.pawsitive.ui.common.navigation.NavigationRoutes
import com.nastena.pawsitive.ui.common.validation.ValidationState
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern

class EditingShelterProfileViewModel(
    mainViewModel: MainViewModel,
    private val _shelterRepository: ShelterRepository,
) : BaseScreenViewModel(mainViewModel) {

    companion object {
        private val PHONE_REGEX = Pattern.compile(
            "^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$")
    }

    private val _phoneState = MutableStateFlow(EditingShelterProfileState.Phone(
            text = "", validation = ValidationState.Valid
        )
    )
    val phoneState: StateFlow<EditingShelterProfileState.Phone> = _phoneState.asStateFlow()

    private val _addressState = MutableStateFlow("")
    val addressState: StateFlow<String> = _addressState.asStateFlow()

    private val _infoState = MutableStateFlow("")
    val infoState: StateFlow<String> = _infoState.asStateFlow()


    override fun onEnter() {
        super.onEnter()

        mainViewModel.hideNavigationBar()

        launchSave(
            operation = {
                _shelterRepository.getShelterProfileData()
            },

            onSuccess = { shelterProfile: ShelterProfileResponse ->
                Log.d("ShelterProfile", "Success: $shelterProfile")
                _addressState.update { shelterProfile.address }
                _infoState.update { shelterProfile.info }
                _phoneState.update {
                    it.copy(
                        text = "",
                        validation = ValidationState.Valid
                    )
                }
            }
        )
    }

    fun onViewEvent(event: EditingShelterProfileEvents) {
        when (event) {
            is EditingShelterProfileEvents.Address.TextUpdated ->
                _addressState.update { event.newText }

            is EditingShelterProfileEvents.Info.TextUpdated ->
                _infoState.update { event.newText }

            is EditingShelterProfileEvents.Phone.TextUpdated ->
                _phoneState.update { it.copy(text = event.newText) }

            EditingShelterProfileEvents.SaveChangedClicked ->
            {
                val trimmedPhone = _phoneState.value.text.trim()
                if (!PHONE_REGEX.matcher(trimmedPhone).matches()) {
                    _phoneState.update { it.copy(validation = ValidationState.InvalidFormat) }
                    return
                } else {
                    _phoneState.update {
                        it.copy(validation = ValidationState.Valid)
                    }
                }

                launchSave(
                    operation = {
                        _shelterRepository.updateShelterProfileData(
                            trimmedPhone,
                            _addressState.value, _infoState.value
                        )
                    },
                    onSuccess = {
                        mainViewModel.navigate(
                            To(
                                NavigationRoutes.SHELTER_PROFILE,
                                Route(NavigationRoutes.SHELTER_PROFILE_EDITING)
                            )
                        )
                    }
                )
            }

            EditingShelterProfileEvents.CancelClicked -> {
                mainViewModel.navigate(
                    To(
                        NavigationRoutes.SHELTER_PROFILE,
                        Route(NavigationRoutes.SHELTER_PROFILE_EDITING)
                    )
                )
            }
        }
    }



}