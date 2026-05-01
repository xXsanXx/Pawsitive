package com.nastena.pawsitive.ui.screens.user.home

import com.nastena.pawsitive.dto.AnimalResponse
import com.nastena.pawsitive.network.NetworkUtils
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
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

    private val _currentAnimalState = MutableStateFlow<UserHomeState.Animal?>(null)
    val currentAnimalState = _currentAnimalState.asStateFlow()

    private var _currentIndex = 0

    private var _currentId: Long? = null

    private var _animals: List<AnimalResponse?> = emptyList()

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        if (_animals.isEmpty()) {
            loadAnimalsRation()
        }
    }

    fun onViewEvent(event: UserHomeEvents) {
        when (event) {
            UserHomeEvents.DetailsClicked -> {
                mainViewModel.navigate(
                    To(
                        NavigationRoute.AnimalDetails(_currentId!!)
                    )
                )
            }

            UserHomeEvents.DislikeClicked -> {
                showNextAnimal()
            }

            UserHomeEvents.LikeClicked -> {
                launchSave(
                    operation = { _userRepository.addToFavorite(_currentId!!) },
                    onSuccess = {
                        showNextAnimal()
                    }
                )
            }
        }
    }


    private fun showNextAnimal() {
        _currentIndex++

        if (_currentIndex < _animals.size) {
            _currentAnimalState.value =
                _animals[_currentIndex]?.let(::responseToView)
            _currentId = _animals[_currentIndex]?.id
        } else {
            _currentAnimalState.value = null

            loadAnimalsRation()
        }
    }

    private fun loadAnimalsRation() {
        launchSave(
            operation = { _userRepository.getRandomAnimalsRation() },

            onSuccess = { response ->

                _animals = response.animals
                _currentIndex = 0

                val firstAnimal = _animals.firstOrNull()

                _currentAnimalState.value =
                    firstAnimal?.let(::responseToView)

                _currentId = firstAnimal?.id
            }
        )
    }

    private fun responseToView(animalResponse: AnimalResponse): UserHomeState.Animal =
        UserHomeState.Animal(
            name = animalResponse.name,
            type = animalResponse.type,
            gender = animalResponse.gender,
            breed = animalResponse.breed,
            photoUrl = animalResponse.animalPhotos.getOrNull(0)?.let { photo: String ->
                NetworkUtils.getAbsoluteFileUrl(photo)
            }
        )

}