package com.nastena.pawsitive.ui.screens.user.details.shelter_info

import com.nastena.pawsitive.dto.AnimalResponse
import com.nastena.pawsitive.dto.ShelterInfoResponse
import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.common.Utils
import com.nastena.pawsitive.ui.common.navigation.Navigation
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

class ShelterInfoViewModel(
    mainViewModel: MainViewModel,
    private val _userRepository: UserRepository,
    private val _filesRepository: FilesRepository,
) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.ShelterInfo::class

    private val _shelterState: MutableStateFlow<ShelterInfoState.Shelter> = MutableStateFlow(
        ShelterInfoState.Shelter(
            name = "",
            email = "",
            phone = "",
            address = "",
            info = ""
        )
    )

    val shelterState: StateFlow<ShelterInfoState.Shelter> = _shelterState.asStateFlow()

    private val _animalsState: MutableStateFlow<List<ShelterInfoState.Animal>> =
        MutableStateFlow(emptyList())

    val animalsState: StateFlow<List<ShelterInfoState.Animal>> = _animalsState.asStateFlow()


    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        val detailsRoute = route as NavigationRoute.ShelterInfo

        loadShelterInfo(detailsRoute.shelterId)
    }

    fun onViewEvent(event: ShelterInfoEvents) {
        when (event) {
            is ShelterInfoEvents.BackClicked -> {
                mainViewModel.navigate(
                    Navigation.Back
                )
            }
        }
    }

    private fun loadShelterInfo(shelterId: Long) {
        launchSave(
            operation = {
                _userRepository.getShelterInfo(shelterId)
            },

            onSuccess = { shelterResponse: ShelterInfoResponse ->
                _shelterState.update {
                    ShelterInfoState.Shelter(
                        name = shelterResponse.name,
                        email = shelterResponse.email,
                        phone = shelterResponse.phone,
                        address = shelterResponse.address,
                        info = shelterResponse.info
                    )
                }

                _animalsState.update {
                    shelterResponse.animals?.map { response: AnimalResponse ->
                        ShelterInfoState.Animal(
                            response.name,
                            response.type,
                            Utils.dateToAge(response.birthDate),
                            response.animalPhotos?.map { photo: String ->
                                _filesRepository.getAbsoluteFileUrl(photo)
                            } ?: emptyList()
                        )
                    } ?: emptyList()
                }
            }
        )
    }


}