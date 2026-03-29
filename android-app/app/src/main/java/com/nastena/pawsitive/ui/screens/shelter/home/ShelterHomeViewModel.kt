package com.nastena.pawsitive.ui.screens.shelter.home

import android.util.Log
import com.nastena.pawsitive.dto.AnimalResponse
import com.nastena.pawsitive.dto.AnimalsResponse
import com.nastena.pawsitive.repository.AnimalRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.NavigationRoutes
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class ShelterHomeViewModel(
    mainViewModel: MainViewModel,
    private val _animalRepository: AnimalRepository
) : BaseScreenViewModel(mainViewModel) {

    private val _animalsState: MutableStateFlow<List<ShelterHomeState.Animal>> = MutableStateFlow(emptyList())
    val animalsState: StateFlow<List<ShelterHomeState.Animal>> = _animalsState.asStateFlow()

    private val _animalIds: MutableList<Long> = mutableListOf()

    override fun onEnter() {
        super.onEnter()

        _animalIds.clear()

        launchSave(
            operation = {
                Log.d("ShelterHome", "Loading animals data")
                _animalRepository.getAnimalsData()
            },

            onSuccess = { animalsResponse: AnimalsResponse ->
                animalsResponse.animalResponses.map { animalResponse: AnimalResponse ->
                    _animalIds.add(animalResponse.id)

                    val birthYear = Instant.ofEpochMilli(animalResponse.birthDate)
                        .atZone(ZoneId.systemDefault()).year
                    val currentYear = LocalDate.now().year
                    ShelterHomeState.Animal(name = animalResponse.name, type = animalResponse.type, age = currentYear - birthYear)

                }
            }
        )


    }

    fun onViewEvent(event: ShelterHomeEvents) {
        when (event) {
            ShelterHomeEvents.AddAnimalClicked ->
                mainViewModel.navigate(
                    To(
                        NavigationRoutes.SHELTER_ADD_ANIMAL
                    ),
                )
            is ShelterHomeEvents.EditingClicked -> TODO()
        }
    }
}