package com.nastena.pawsitive.ui.screens.user.profile

import android.util.Log
import com.nastena.pawsitive.dto.AdoptionStatus
import com.nastena.pawsitive.dto.UserAdoptionsResponse
import com.nastena.pawsitive.dto.UserProfileResponse
import com.nastena.pawsitive.repository.AccountRepository
import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.common.isFinal
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.Navigation.To.PopUpType.Route
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute.AnimalDetails
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import com.nastena.pawsitive.ui.screens.user.profile.UserProfileState.ConfirmForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

class UserProfileViewModel(
    mainViewModel: MainViewModel,
    private val _userRepository: UserRepository,
    private val _accountRepository: AccountRepository,
    private val _filesRepository: FilesRepository,
) : BaseScreenViewModel(mainViewModel) {


    override val expectedRouteType: KClass<*> = NavigationRoute.UserProfile::class
    private val _emailState = MutableStateFlow("")
    val emailState: StateFlow<String> = _emailState.asStateFlow()

    private val _nameState = MutableStateFlow("")
    val nameState: StateFlow<String> = _nameState.asStateFlow()

    private val _adoptionState: MutableStateFlow<List<UserProfileState.Requests>> =
        MutableStateFlow(emptyList())

    val adoptionState: StateFlow<List<UserProfileState.Requests>> = _adoptionState.asStateFlow()

    private val _confirmFormState = MutableStateFlow<ConfirmForm?>(null)

    val confirmFormState: StateFlow<ConfirmForm?> =
        _confirmFormState.asStateFlow()

    private val _animalIds: MutableList<Long> = mutableListOf()
    private val _requestIds: MutableList<Long> = mutableListOf()


    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        _emailState.update { "" }
        _nameState.update { "" }
        _adoptionState.update { emptyList() }
        _animalIds.clear()
        _requestIds.clear()

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
                        _requestIds.add(userAdoptionResponse.id)

                        UserProfileState.Requests(
                            animalName = userAdoptionResponse.animalName,
                            shelterName = userAdoptionResponse.shelterName,
                            status = userAdoptionResponse.status,
                            photoUrls = userAdoptionResponse.animalPhotos.map { url ->
                                _filesRepository.getAbsoluteFileUrl(url)
                            }

                        )

                    }
                }

            }
        )
    }

    fun onViewEvent(event: UserProfileEvents) {
        when (event) {
            UserProfileEvents.LogoutClicked -> onLogoutClicked()
            is UserProfileEvents.CancelRequestClicked -> {
                val request = _adoptionState.value[event.index]

                if (request.status == AdoptionStatus.PENDING) {
                    _confirmFormState.value =
                        ConfirmForm(
                            event.index, isVisible = true,
                            formType = UserProfileState.ConfirmFormType.CANCEL
                        )
                }
            }

            is UserProfileEvents.GoToAnimalClicked -> {
                mainViewModel.navigate(
                    To(
                        AnimalDetails(_animalIds[event.index]),
                        Route(NavigationRoute.UserProfile::class)
                    )
                )
            }

            is UserProfileEvents.HideRequestClicked -> {
                val request = _adoptionState.value[event.index]

                if (request.status.isFinal()) {
                    _confirmFormState.value =
                        ConfirmForm(
                            event.index, isVisible = true,
                            formType = UserProfileState.ConfirmFormType.HIDE
                        )
                }
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

    fun onConfirmCancel(confirmed: Boolean) {
        val dialogState = _confirmFormState.value ?: return
        if (confirmed) {
            when (dialogState.formType) {
                UserProfileState.ConfirmFormType.CANCEL -> cancelForm(dialogState.index)
                UserProfileState.ConfirmFormType.HIDE -> hideForm(dialogState.index)
            }
        }
        _confirmFormState.value = null
    }

    private fun cancelForm(index: Int) {
        val animalId = _animalIds[index]

        launchSave(
            operation = { _userRepository.cancelAdoptionRequest(animalId) },
            onSuccess = {
                _adoptionState.update { it.filterIndexed { i, _ -> i != index } }
                _animalIds.removeAt(index)
            }
        )
    }

    private fun hideForm(index: Int) {
        val requestId = _requestIds[index]

        launchSave(
            operation = {
                _userRepository.hideRequest(requestId)
            },
            onSuccess = {
                _adoptionState.update { requests ->
                    requests.take(index) + requests.dropLast(index + 1)
                }
                _animalIds.removeAt(index)
                _requestIds.removeAt(index)
            }
        )
    }

//    private fun cancelAdoptionRequest(animalIndex: Int) {
//        val animalId: Long = _animalIds[animalIndex]
//
//        launchSave(
//            operation = { _userRepository.cancelAdoptionRequest(animalId) },
//            onSuccess = {
//                mainViewModel.showMessage(R.string.request_cancelled)
//                _adoptionState.update {
//                    _adoptionState.value.take(animalIndex) +
//                            _adoptionState.value.dropLast(animalIndex + 1)
//                }
//                _animalIds.removeIf { id: Long -> animalId == id }
//            }
//        )
//    }
}
