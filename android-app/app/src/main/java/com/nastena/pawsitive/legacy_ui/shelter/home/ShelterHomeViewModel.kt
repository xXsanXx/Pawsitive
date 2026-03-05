package com.nastena.pawsitive.legacy_ui.shelter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.repository.AnimalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShelterHomeViewModel(
    private val animalRepository: AnimalRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ShelterHomeState>(ShelterHomeState.Loading)
    val state: StateFlow<ShelterHomeState> = _state

    init {
        loadMyAnimals()
    }

    fun loadMyAnimals() {
        viewModelScope.launch {
            try {
                val animals = animalRepository.getMyAnimals()
                _state.value = ShelterHomeState.Success(animals)
            } catch (e: Exception) {
                _state.value = ShelterHomeState.Error("Ошибка")
            }
        }
    }
}