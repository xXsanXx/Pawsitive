package com.nastena.pawsitive.ui.user.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.data.repository.AnimalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserHomeViewModel(
    private val animalRepository: AnimalRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UserHomeState>(UserHomeState.Loading)
    val state: StateFlow<UserHomeState> = _state

    init {
        loadAnimals()
    }

    private fun loadAnimals() {
        viewModelScope.launch {
            try {
                val animals = animalRepository.getAnimals()
                _state.value = UserHomeState.Success(animals)
            } catch (e: Exception) {
                _state.value = UserHomeState.Error("Ошибка загрузки")
            }
        }
    }
}