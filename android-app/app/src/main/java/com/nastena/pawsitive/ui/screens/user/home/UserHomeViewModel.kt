package com.nastena.pawsitive.ui.screens.user.home

import com.nastena.pawsitive.dto.ShelterAnimalResponse
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.reflect.KClass

class UserHomeViewModel(
    mainViewModel: MainViewModel,
    private val _shelterRepository: ShelterRepository,
) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.UserHome::class

    private val _currentAnimalState = MutableStateFlow<ShelterAnimalResponse?>(null)
    val currentAnimalState = _currentAnimalState.asStateFlow()

    private var _currentIndex = 0

    private var _animals: List<ShelterAnimalResponse> = emptyList()

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        launchSave(
            operation = { _shelterRepository.getShelterAnimalsData() },

            onSuccess = { response ->

                _animals = response.animals
                _currentIndex = 0

                _currentAnimalState.value = _animals.firstOrNull()
            }
        )

    }

    fun onViewEvent(event: UserHomeEvents) {
        when (event) {
            UserHomeEvents.DetailsClicked -> TODO()
            UserHomeEvents.DislikeClicked -> TODO()
            UserHomeEvents.LikeClicked -> TODO()
        }
    }


    private fun showNextAnimal() {

    }


}