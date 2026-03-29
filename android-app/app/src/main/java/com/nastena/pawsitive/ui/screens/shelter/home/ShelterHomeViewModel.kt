package com.nastena.pawsitive.ui.screens.shelter.home

import android.util.Log
import com.nastena.pawsitive.dto.ShelterAnimalResponse
import com.nastena.pawsitive.dto.ShelterAnimalsResponse
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.NavigationRoutes
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.math.log

class ShelterHomeViewModel(
    mainViewModel: MainViewModel,
    private val _shelterRepository: ShelterRepository
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
                _shelterRepository.getShelterAnimalsData()
            },

            onSuccess = { animalsResponse: ShelterAnimalsResponse ->
                Log.i("ShelterHome", "Got ${animalsResponse.animals?.size} animals")

                if (animalsResponse.animals != null) {
                    _animalsState.update {
                        animalsResponse.animals.map { animalResponse: ShelterAnimalResponse ->
                            _animalIds.add(animalResponse.id)

                            val birthYear = Instant.ofEpochSecond(animalResponse.birthDate)
                                .atZone(ZoneId.systemDefault()).year
                            val currentYear = LocalDate.now().year
                            ShelterHomeState.Animal(name = animalResponse.name, type = animalResponse.type, age = currentYear - birthYear)

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
                        NavigationRoutes.SHELTER_ADD_ANIMAL
                    ),
                )
            is ShelterHomeEvents.EditingClicked -> TODO()
        }
    }
}