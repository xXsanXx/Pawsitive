package com.nastena.pawsitive.ui.screens.shelter.animal.add

import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.Navigation.To.PopUpType.Route
import com.nastena.pawsitive.ui.common.navigation.NavigationRoutes
import com.nastena.pawsitive.ui.common.validation.ValidationState
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern

class ShelterAddAnimalViewModel(
    mainViewModel: MainViewModel,
    private val _shelterRepository: ShelterRepository
) : BaseScreenViewModel(mainViewModel) {

    companion object {
        private val NAME_REGEX = Pattern.compile("^[A-Za-zА-Яа-я\\s]{2,50}$")
        private const val MAX_ANIMAL_PHOTOS = 3
        private const val MAX_PASSPORT_ANIMAL_PHOTOS = 15
    }

    private val _nameState = MutableStateFlow(
        ShelterAddAnimalState.Name(
            text = "", validation = ValidationState.Valid
        )
    )

    val nameState: StateFlow<ShelterAddAnimalState.Name> = _nameState.asStateFlow()

    private val _typeState = MutableStateFlow(
        ShelterAddAnimalState.Type(
            selected = null,
            isExpended = false, isValid = true
        )
    )

    val typeState: StateFlow<ShelterAddAnimalState.Type> = _typeState.asStateFlow()

    private val _breedState = MutableStateFlow(
        ShelterAddAnimalState.Breed(
            selected = null,
            isExpended = false, isValid = true
        )
    )

    val breedState: StateFlow<ShelterAddAnimalState.Breed> = _breedState.asStateFlow()

    private val _genderState = MutableStateFlow(
        ShelterAddAnimalState.Gender(
            selected = null,
            isExpended = false, isValid = true
        )
    )

    val genderState: StateFlow<ShelterAddAnimalState.Gender> = _genderState.asStateFlow()

    private val _descriptionState = MutableStateFlow(
        ShelterAddAnimalState.Description(
            text = "", validation = ValidationState.Valid
        )
    )

    private val _birthDateState = MutableStateFlow(ShelterAddAnimalState.BirthDate(date = null, isValid = true))

    val birthDateState: StateFlow<ShelterAddAnimalState.BirthDate> = _birthDateState.asStateFlow()

    val descriptionState: StateFlow<ShelterAddAnimalState.Description> =
        _descriptionState.asStateFlow()

    private val _animalPhotosState = MutableStateFlow(ShelterAddAnimalState.Photos())
    val animalPhotosState: StateFlow<ShelterAddAnimalState.Photos> = _animalPhotosState.asStateFlow()

    private val _animalPassportPhotosState = MutableStateFlow(ShelterAddAnimalState.Photos())
    val animalPassportPhotosState: StateFlow<ShelterAddAnimalState.Photos> = _animalPassportPhotosState.asStateFlow()

    override fun onEnter() {
        super.onEnter()

        _nameState.update { it.copy(text = "", validation = ValidationState.Valid) }

        _typeState.update {
            it.copy(
                selected = null,
                isExpended = false, isValid = true
            )
        }

        _breedState.update {
            it.copy(
                selected = null,
                isExpended = false, isValid = true
            )
        }

        _genderState.update {
            it.copy(
                selected = null,
                isExpended = false, isValid = true
            )
        }

        _descriptionState.update { it.copy(text = "", validation = ValidationState.Valid) }

        _birthDateState.update { it.copy(date = null, isValid = true) }


        _animalPhotosState.update { it.copy(animalPhotos = emptyList()) }

        _animalPassportPhotosState.update { it.copy(animalPassportPhotos = emptyList()) }

    }

    fun onViewEvent(event: ShelterAddAnimalEvents) {
        when (event) {

            is ShelterAddAnimalEvents.BirthDate.DateSelected -> {
                _birthDateState.update { it.copy(date = event.date) }
            }

            ShelterAddAnimalEvents.Breed.MenuDismissed -> {
                _breedState.update { it.copy(isExpended = false) }
            }

            is ShelterAddAnimalEvents.Breed.BreedSelected -> {
                _breedState.update {
                    it.copy(
                        isExpended = false,
                        selected = event.breed
                    )
                }
            }

            ShelterAddAnimalEvents.Breed.ClickedBreed -> {
                _breedState.update { it.copy(isExpended = !it.isExpended) }
            }

            ShelterAddAnimalEvents.Gender.ClickedGender -> {
                _genderState.update { it.copy(isExpended = !it.isExpended) }
            }

            ShelterAddAnimalEvents.Gender.MenuDismissed -> {
                _genderState.update { it.copy(isExpended = false) }
            }

            is ShelterAddAnimalEvents.Gender.GenderSelected -> {
                _genderState.update {
                    it.copy(
                        isExpended = false,
                        selected = event.gender
                    )
                }
            }

            ShelterAddAnimalEvents.Type.ClickedType -> {
                _typeState.update { it.copy(isExpended = !it.isExpended) }
            }

            ShelterAddAnimalEvents.Type.MenuDismissed -> {
                _typeState.update { it.copy(isExpended = false) }
            }

            is ShelterAddAnimalEvents.Type.TypeSelected -> {
                _typeState.update {
                    it.copy(
                        isExpended = false,
                        selected = event.type
                    )
                }
                _breedState.update {
                    it.copy(selected = null)
                }
            }

            is ShelterAddAnimalEvents.Description.TextUpdated ->
                _descriptionState.update { it.copy(text = event.newText) }

            is ShelterAddAnimalEvents.Name.TextUpdated ->
                _nameState.update { it.copy(text = event.newText) }

            ShelterAddAnimalEvents.CancelClicked -> {
                mainViewModel.navigate(
                    To(
                        NavigationRoutes.SHELTER_HOME,
                        Route(NavigationRoutes.SHELTER_ADD_ANIMAL)
                    )
                )
            }

            ShelterAddAnimalEvents.AddClicked -> {
                create()
            }

            is ShelterAddAnimalEvents.Photos.AddAnimalPhotos -> {
                addAnimalPhoto(event.uri)
            }

            is ShelterAddAnimalEvents.Photos.AddPassportAnimalPhotos -> {
                addPassportPhoto(event.uri)
            }

        }
    }

    private fun create() {

        val trimmedName = _nameState.value.text.trim()
        val trimmedDescription = _descriptionState.value.text.trim()

        val isDescriptionValid = trimmedDescription.isNotBlank()
        _descriptionState.update {
            it.copy(validation = if (isDescriptionValid) ValidationState.Valid else ValidationState.Empty)
        }

        if (trimmedName.isBlank()) {
            _nameState.update { it.copy(validation = ValidationState.Empty) }
        } else if (
            trimmedName.length < 2 ||
            trimmedName.length > 50 ||
            !NAME_REGEX.matcher(trimmedName).matches()
        ) {
            _nameState.update { it.copy(validation = ValidationState.InvalidFormat) }
        } else {
            _nameState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }

        _typeState.update { it.copy(isValid = it.selected != null) }

        _breedState.update { it.copy(isValid = it.selected != null) }

        _genderState.update { it.copy(isValid = it.selected != null) }

        val birthDate = _birthDateState.value.date
        val isBirthDateValid = birthDate != null && birthDate < System.currentTimeMillis()

        val isAllValid = _nameState.value.validation is ValidationState.Valid &&
                _descriptionState.value.validation is ValidationState.Valid &&
                _typeState.value.isValid && _breedState.value.isValid &&
                _genderState.value.isValid && isDescriptionValid && isBirthDateValid

        if (isAllValid) {
            launchSave(
                operation = {
                    _shelterRepository.createAnimal(
                        name = trimmedName,
                        type = _typeState.value.selected!!,
                        breed = _breedState.value.selected!!,
                        gender = _genderState.value.selected!!,
                        description = trimmedDescription,
                        birthDate = birthDate!!,
                        photoPaths = _animalPhotosState.value.animalPhotos,
                        passportPaths = _animalPassportPhotosState.value.animalPassportPhotos
                    )
                },
                onSuccess = {
                    mainViewModel.navigate(
                        To(
                            NavigationRoutes.SHELTER_HOME,
                            Route(NavigationRoutes.SHELTER_ADD_ANIMAL)
                        )
                    )
                }
            )
        }

    }

    private fun addAnimalPhoto(uri: String) {
        val currentPhotos = _animalPhotosState.value.animalPhotos
        if (currentPhotos.size < MAX_ANIMAL_PHOTOS) {
            _animalPhotosState.update {
                it.copy(animalPhotos = currentPhotos + uri)
            }
        }
    }

    private fun addPassportPhoto(uri: String) {
        val currentPhotos = _animalPassportPhotosState.value.animalPassportPhotos
        if (currentPhotos.size < MAX_PASSPORT_ANIMAL_PHOTOS) {
            _animalPassportPhotosState.update {
                it.copy(animalPassportPhotos = currentPhotos + uri)
            }
        }
    }




}