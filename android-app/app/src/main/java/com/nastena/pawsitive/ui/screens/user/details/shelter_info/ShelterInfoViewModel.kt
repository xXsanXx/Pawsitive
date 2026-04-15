package com.nastena.pawsitive.ui.screens.user.details.shelter_info

import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.ui.common.Utils
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.Navigation.To.PopUpType.Route
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
    private val _shelterRepository: ShelterRepository,
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

        loadShelterInfo()
        loadShelterAnimals()
    }

    fun onViewEvent(event: ShelterInfoEvents) {
        when (event) {
            is ShelterInfoEvents.BackToDetailsClicked -> {
                mainViewModel.navigate(
                    To(
                        NavigationRoute.AnimalDetails(event.animalId),
                        Route(NavigationRoute.ShelterInfo::class)
                    )
                )
            }
        }
    }

    private fun loadShelterInfo() {
        launchSave(
            operation = {
                _shelterRepository.getShelterProfileData()
            },

            onSuccess = { response ->
                _shelterState.value =
                    ShelterInfoState.Shelter(
                        name = response.name,
                        email = response.email,
                        phone = response.phone,
                        address = response.address,
                        info = response.info
                    )
            }
        )
    }

    private fun loadShelterAnimals() {
        launchSave(
            operation = {
                _shelterRepository.getShelterAnimalsData()
            },

            onSuccess = { animalsResponse ->
                val animals = animalsResponse.animals?.map { animal ->
                    ShelterInfoState.Animal(
                        name = animal.name,
                        type = animal.type,
                        age = Utils.dateToAge(animal.birthDate),
                        photoUrls = animal.animalPhotos.map {
                            _filesRepository.getAbsoluteFileUrl(it)
                        }
                    )

                } ?: emptyList()

                _animalsState.update { animals }
            }
        )
    }


}