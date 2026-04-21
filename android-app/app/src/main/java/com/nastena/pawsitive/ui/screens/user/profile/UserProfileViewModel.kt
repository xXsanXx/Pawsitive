package com.nastena.pawsitive.ui.screens.user.profile

import android.util.Log
import com.nastena.pawsitive.R
import com.nastena.pawsitive.dto.AdoptionStatus
import com.nastena.pawsitive.dto.UserAdoptionsResponse
import com.nastena.pawsitive.dto.UserProfileResponse
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

class UserProfileViewModel(
    mainViewModel: MainViewModel,
    private val _userRepository: UserRepository,
    private val _accountRepository: AccountRepository
) : BaseScreenViewModel(mainViewModel) {


    override val expectedRouteType: KClass<*> = NavigationRoute.UserProfile::class
    private val _emailState = MutableStateFlow("")
    val emailState: StateFlow<String> = _emailState.asStateFlow()

    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> = _nameState.asStateFlow()

    private val _adoptionState: MutableStateFlow<List<UserProfileState.Requests>> =
        MutableStateFlow(emptyList())

    val adoptionState: StateFlow<List<UserProfileState.Requests>> = _adoptionState.asStateFlow()

    private val _animalIds: MutableList<Long> = mutableListOf()

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        _emailState.update { "" }
        _nameState.update { "" }
        _adoptionState.update { emptyList() }
        _animalIds.clear()

        launchSave(
            operation = {
                Log.d("UserProfile", "Loading profile data")
                _userRepository.getProfileData()
            },

            onSuccess = { userProfile: UserProfileResponse ->
                Log.d("UserProfile", "Success: $userProfile")
                _emailState.update { userProfile.email }
                _nameState.update { userProfile.name }
            }
        )

        launchSave(
            operation = {
                Log.d("UserProfileForm", "My forms")
                _userRepository.getUserRequests()
            },
            onSuccess = { userAdoptionsResponse: UserAdoptionsResponse ->
                Log.i(
                    "UserRequests",
                    "Got ${userAdoptionsResponse.adoptionsResponse} userAdoptionsResponse"
                )

                _adoptionState.update {
                    userAdoptionsResponse.adoptionsResponse.map { userAdoptionResponse ->

                        _animalIds.add(userAdoptionResponse.animalId)

                        UserProfileState.Requests(
                            animalName = userAdoptionResponse.animalName,
                            shelterName = userAdoptionResponse.shelterName,
                            status = userAdoptionResponse.status

                        )

                    }
                }

            }
        )
    }

    fun onViewEvent(event: UserProfileViewEvents) {
        when (event) {
            UserProfileViewEvents.LogoutClicked -> onLogoutClicked()
            is UserProfileViewEvents.CancelRequestClicked -> {
                val request: UserProfileState.Requests = _adoptionState.value.get(event.index)
                assert(request.status == AdoptionStatus.PENDING)
                cancelAdoptionRequest(event.index)
            }
        }
    }

    fun onLogoutClicked() {
        launchSave(
            operation = { _accountRepository.logout() },
            onSuccess = {
                mainViewModel.navigate(
                    To(
                        NavigationRoute.Login,
                        To.PopUpType.Origin
                    )
                )
            }
        )
    }

    private fun cancelAdoptionRequest(animalIndex: Int) {
        val animalId: Long = _animalIds[animalIndex]

        launchSave(
            operation = { _userRepository.cancelAdoptionRequest(animalId) },
            onSuccess = {
                mainViewModel.showMessage(R.string.request_cancelled)
                _adoptionState.update {
                    _adoptionState.value.take(animalIndex) +
                            _adoptionState.value.dropLast(animalIndex + 1)
                }
                _animalIds.removeIf { id: Long -> animalId == id }
            }
        )
    }
}
