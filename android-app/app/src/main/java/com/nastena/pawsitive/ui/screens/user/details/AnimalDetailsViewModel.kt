package com.nastena.pawsitive.ui.screens.user.details

import android.util.Log
import com.nastena.pawsitive.dto.AnimalBreed
import com.nastena.pawsitive.dto.AnimalGender
import com.nastena.pawsitive.dto.AnimalType
import com.nastena.pawsitive.network.NetworkUtils
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

class AnimalDetailsViewModel(
    mainViewModel: MainViewModel,
    private val _userRepository: UserRepository
) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.AnimalDetails::class

    private val _animalState: MutableStateFlow<AnimalDetailsState.Animal> = MutableStateFlow(
        AnimalDetailsState.Animal(
            name = "",
            type = AnimalType.CAT,
            breed = AnimalBreed.METIS,
            gender = AnimalGender.MALE,
            birthDate = 0,
            photosUrl = emptyList()
        )
    )

    val animalState: StateFlow<AnimalDetailsState.Animal> = _animalState.asStateFlow()

    private var _shelterId: Long? = null
    private var _animalId: Long? = null

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        val detailsRoute = route as NavigationRoute.AnimalDetails

        launchSave(
            operation = {
                Log.d("Details", "Loading details")
                _userRepository.getAnimalDetails(animalId = detailsRoute.animalId)
            },

            onSuccess = { response ->
                _shelterId = response.shelterId
                _animalState.update {
                    AnimalDetailsState.Animal(
                        name = response.name,
                        type = response.type,
                        breed = response.breed,
                        gender = response.gender,
                        birthDate = response.birthDate,
                        photosUrl = response.animalPhotos
                            ?.map { NetworkUtils.getAbsoluteFileUrl(it) }
                            ?: emptyList()
                    )
                }

            }
        )
    }

    fun onViewEvent(event: AnimalDetailsEvents) {
        when (event) {
            AnimalDetailsEvents.GoToFormClicked -> {
                _animalId?.let { animalId ->
                    mainViewModel.navigate(
                        To(
                            NavigationRoute.Form(animalId)
                        )
                    )
                }
            }

            AnimalDetailsEvents.ShelterInfoClicked -> {
                mainViewModel.navigate(
                    To(
                        NavigationRoute.ShelterInfo(_shelterId!!),
                    )
                )

            }
        }
    }

}