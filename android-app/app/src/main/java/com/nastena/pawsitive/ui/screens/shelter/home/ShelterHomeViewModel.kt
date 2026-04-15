package com.nastena.pawsitive.ui.screens.shelter.home

import android.util.Log
import com.nastena.pawsitive.dto.ShelterAnimalResponse
import com.nastena.pawsitive.dto.ShelterAnimalsResponse
import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.ui.common.Utils
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute.Shelter.Animal.Edit
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass

class ShelterHomeViewModel(
    mainViewModel: MainViewModel,
    private val _shelterRepository: ShelterRepository,
    private val _filesRepository: FilesRepository,
    override val expectedRouteType: KClass<*> = NavigationRoute.ShelterHome::class,
) : BaseScreenViewModel(mainViewModel) {

    private val _animalsState: MutableStateFlow<List<ShelterHomeState.Animal>> =
        MutableStateFlow(emptyList())
    val animalsState: StateFlow<List<ShelterHomeState.Animal>> = _animalsState.asStateFlow()

    private val _animalIds: MutableList<Long> = mutableListOf()

    private val _confirmAnimalDelete = MutableStateFlow<ShelterHomeState.ConfirmAnimalDelete?>(null)
    val confirmAnimalDelete: StateFlow<ShelterHomeState.ConfirmAnimalDelete?> =
        _confirmAnimalDelete.asStateFlow()

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        _animalIds.clear()

        launchSave(
            operation = {
                Log.d("ShelterHome", "Loading animals data")
                _shelterRepository.getShelterAnimalsData()
            },

            onSuccess = { animalsResponse: ShelterAnimalsResponse ->
                Log.i("ShelterHome", "Got ${animalsResponse.animals?.size} animals")

                if (animalsResponse.animals != null) {
                    _animalsState.update {
                        animalsResponse.animals.map { animalResponse: ShelterAnimalResponse ->
                            _animalIds.add(animalResponse.id)

                            ShelterHomeState.Animal(
                                name = animalResponse.name,
                                type = animalResponse.type,
                                age = Utils.dateToAge(dateMillis = animalResponse.birthDate),
                                photoUrls = animalResponse.animalPhotos.map { url ->
                                    _filesRepository.getAbsoluteFileUrl(url)
                                }
                            )

                        }
                    }
                }
            }
        )


    }

    fun onViewEvent(event: ShelterHomeEvents) {
        when (event) {
            ShelterHomeEvents.AddAnimalClicked ->
                mainViewModel.navigate(
                    To(
                        NavigationRoute.Shelter.Animal.Add
                    ),
                )

            is ShelterHomeEvents.EditingClicked -> {
                mainViewModel.navigate(
                    To(
                        Edit(animalId = _animalIds[event.index])
                    )
                )
            }

            is ShelterHomeEvents.RemoveClicked -> {
                _confirmAnimalDelete.value =
                    ShelterHomeState.ConfirmAnimalDelete(index = event.index)
            }
        }
    }

    fun onConfirmDelete(confirmed: Boolean) {
        val dialogState = _confirmAnimalDelete.value ?: return
        if (confirmed) removeAnimal(dialogState.index)
        _confirmAnimalDelete.value = null
    }

    private fun removeAnimal(index: Int) {
        val animalId = _animalIds[index]

        launchSave(
            operation = { _shelterRepository.removeAnimal(animalId) },
            onSuccess = {
                _animalsState.update { it.filterIndexed { i, _ -> i != index } }
                _animalIds.removeAt(index)
            }
        )
    }

}