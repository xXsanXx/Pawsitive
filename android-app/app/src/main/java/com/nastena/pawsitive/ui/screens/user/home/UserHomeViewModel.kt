package com.nastena.pawsitive.ui.screens.user.home

import com.nastena.pawsitive.dto.AnimalResponse
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.reflect.KClass

class UserHomeViewModel(
    mainViewModel: MainViewModel,
    private val _userRepository: UserRepository,
) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.UserHome::class

    private val _currentAnimalState = MutableStateFlow<AnimalResponse?>(null)
    val currentAnimalState = _currentAnimalState.asStateFlow()

    private var _currentIndex = 0

    private var _animals: List<AnimalResponse?> = emptyList()

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        loadAnimalsRation()

    }

    fun onViewEvent(event: UserHomeEvents) {
        when (event) {
            UserHomeEvents.DetailsClicked -> TODO()

            UserHomeEvents.DislikeClicked -> {
                showNextAnimal()
            }

            UserHomeEvents.LikeClicked -> TODO()
        }
    }


    private fun showNextAnimal() {
        _currentIndex++

        if (_currentIndex < _animals.size) {
            _currentAnimalState.value = _animals[_currentIndex]
        } else {
            _currentAnimalState.value = null

            loadAnimalsRation()
        }
    }

    private fun loadAnimalsRation() {
        launchSave(
            operation = { _userRepository.getRandomAnimalsRatio() },

            onSuccess = { response ->

                _animals = response.animals
                _currentIndex = 0

                _currentAnimalState.value = _animals.firstOrNull()
            }
        )
    }

}