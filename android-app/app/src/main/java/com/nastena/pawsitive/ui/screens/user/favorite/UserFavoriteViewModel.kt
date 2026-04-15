package com.nastena.pawsitive.ui.screens.user.favorite

import android.util.Log
import com.nastena.pawsitive.dto.AnimalsResponse
import com.nastena.pawsitive.network.NetworkUtils
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.common.Utils
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

class UserFavoriteViewModel(
    mainViewModel: MainViewModel,
    private val _userRepository: UserRepository

) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.Favorite::class

    private val _animalsState: MutableStateFlow<List<UserFavoriteState.Animal>> =
        MutableStateFlow(emptyList())

    val animalsState: StateFlow<List<UserFavoriteState.Animal>> = _animalsState.asStateFlow()

    private val _animalIds: MutableList<Long> = mutableListOf()

    private val _confirmAnimalDelete =
        MutableStateFlow<UserFavoriteState.ConfirmAnimalDelete?>(null)
    val confirmAnimalDelete: StateFlow<UserFavoriteState.ConfirmAnimalDelete?> =
        _confirmAnimalDelete.asStateFlow()


    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        _animalIds.clear()
        _animalsState.update { emptyList() }

        launchSave(
            operation = {
                Log.d("Favorite", "Loading favorites")
                _userRepository.getFavorites()
            },

            onSuccess = { animalsResponse: AnimalsResponse ->
                Log.i("Favorite", "Got ${animalsResponse.animals.size} animals")

                _animalsState.update {

                    animalsResponse.animals.map { animalResponse ->
                        _animalIds.add(animalResponse.id)
                        UserFavoriteState.Animal(
                            name = animalResponse.name,
                            type = animalResponse.type,
                            age = Utils.dateToAge(animalResponse.birthDate),
                            photoUrl = animalResponse.animalPhotos?.getOrNull(0)
                                ?.let { photo: String ->
                                    NetworkUtils.getAbsoluteFileUrl(photo)
                                }
                        )
                    }
                }
            }
        )
    }

    fun onViewEvent(event: UserFavoriteEvents) {
        when (event) {
            is UserFavoriteEvents.GoToAnimalClicked -> {
                mainViewModel.navigate(
                    To(NavigationRoute.AnimalDetails(animalId = _animalIds[event.index]))
                )
            }

            is UserFavoriteEvents.RemoveClicked -> {
                _confirmAnimalDelete.value =
                    UserFavoriteState.ConfirmAnimalDelete(index = event.index)
            }
        }
    }

    fun onConfirmDelete(confirmed: Boolean) {
        val dialogState = _confirmAnimalDelete.value ?: return
        if (confirmed) removeAnimalFromFavorite(dialogState.index)
        _confirmAnimalDelete.value = null
    }

    private fun removeAnimalFromFavorite(index: Int) {
        val animalId = _animalIds[index]

        launchSave(
            operation = { _userRepository.removeFromFavorite(animalId) },
            onSuccess = {
                _animalsState.update { it.filterIndexed { i, _ -> i != index } }
                _animalIds.removeAt(index)
            }
        )
    }
}