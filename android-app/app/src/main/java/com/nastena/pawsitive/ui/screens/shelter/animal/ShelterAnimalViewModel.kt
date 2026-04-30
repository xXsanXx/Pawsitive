package com.nastena.pawsitive.ui.screens.shelter.animal

import android.util.Log
import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation.To
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.common.validation.ValidationState
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import com.nastena.pawsitive.utils.AnimalUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern
import kotlin.reflect.KClass

class ShelterAnimalViewModel(
    mainViewModel: MainViewModel,
    private val _shelterRepository: ShelterRepository,
    private val _filesRepository: FilesRepository,
) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.Shelter.Animal::class

    companion object {
        private val NAME_REGEX = Pattern.compile("^[A-Za-zА-Яа-я\\s]{2,50}$")
        private const val MAX_ANIMAL_PHOTOS = 3
        private const val MAX_PASSPORT_ANIMAL_PHOTOS = 15

    }

    private var _mode = MutableStateFlow<ShelterAnimalState.Mode>(ShelterAnimalState.Mode.Add)
    val mode: StateFlow<ShelterAnimalState.Mode> = _mode.asStateFlow()

    private val _nameState = MutableStateFlow(
        ShelterAnimalState.Name(
            text = "", validation = ValidationState.Valid
        )
    )

    val nameState: StateFlow<ShelterAnimalState.Name> = _nameState.asStateFlow()

    private val _typeState = MutableStateFlow(
        ShelterAnimalState.Type(
            selected = null,
            isExpended = false, isValid = true
        )
    )

    val typeState: StateFlow<ShelterAnimalState.Type> = _typeState.asStateFlow()

    private val _breedState = MutableStateFlow(
        ShelterAnimalState.Breed(
            selected = null,
            options = emptySet(),
            isExpended = false,
            isValid = true
        )
    )

    val breedState: StateFlow<ShelterAnimalState.Breed> = _breedState.asStateFlow()

    private val _genderState = MutableStateFlow(
        ShelterAnimalState.Gender(
            selected = null,
            isExpended = false, isValid = true
        )
    )

    val genderState: StateFlow<ShelterAnimalState.Gender> = _genderState.asStateFlow()

    private val _descriptionState = MutableStateFlow(
        ShelterAnimalState.Description(
            text = "", validation = ValidationState.Valid
        )
    )

    private val _birthDateState =
        MutableStateFlow(ShelterAnimalState.BirthDate(date = null, isValid = true))

    val birthDateState: StateFlow<ShelterAnimalState.BirthDate> = _birthDateState.asStateFlow()

    val descriptionState: StateFlow<ShelterAnimalState.Description> =
        _descriptionState.asStateFlow()

    private val _animalPhotosState = MutableStateFlow(ShelterAnimalState.Photos())
    val animalPhotosState: StateFlow<ShelterAnimalState.Photos> = _animalPhotosState.asStateFlow()

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

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
        _animalPhotosState.update { it.copy(animal = emptyList(), passport = emptyList()) }

        when (route) {
            NavigationRoute.Shelter.Animal.Add -> {
                _mode.update { ShelterAnimalState.Mode.Add }
            }

            is NavigationRoute.Shelter.Animal.Edit -> {
                launchSave(
                    operation = { _shelterRepository.getAnimal(animalId = route.animalId) },
                    onSuccess = { animalResponse ->
                        val originalAnimalPhotos = ShelterAnimalState.Mode.Edit.OriginalPhotos(
                            filenames = animalResponse.animalPhotos ?: emptyList(),
                            filepath = animalResponse.animalPhotos?.map { filename: String ->
                                _filesRepository.getAbsoluteFileUrl(filename)
                            } ?: emptyList()
                        )

                        val originalPassportPhotos = ShelterAnimalState.Mode.Edit.OriginalPhotos(
                            filenames = animalResponse.passportPhotos ?: emptyList(),
                            filepath = animalResponse.passportPhotos?.map { filename: String ->
                                _filesRepository.getAbsoluteFileUrl(filename)
                            } ?: emptyList()
                        )

                        _mode.update {
                            ShelterAnimalState.Mode.Edit(
                                idAnimal = animalResponse.id,
                                originalAnimalPhotos = originalAnimalPhotos,
                                originalPassportPhotos = originalPassportPhotos
                            )
                        }

                        _nameState.update { it.copy(text = animalResponse.name) }
                        _typeState.update {
                            it.copy(
                                selected = animalResponse.type,
                            )
                        }
                        _breedState.update {
                            it.copy(
                                selected = animalResponse.breed,
                                options = AnimalUtils.getBreedForAnimalType(animalResponse.type)
                            )
                        }
                        _genderState.update {
                            it.copy(
                                selected = animalResponse.gender,
                            )
                        }
                        _descriptionState.update { it.copy(text = animalResponse.description) }
                        _birthDateState.update { it.copy(date = animalResponse.birthDate) }
                        _animalPhotosState.update {
                            it.copy(
                                animal = originalAnimalPhotos.filepath,
                                passport = originalPassportPhotos.filepath
                            )
                        }
                    }
                )
            }

            else -> throw IllegalArgumentException(
                "Expected route to be ${NavigationRoute.Shelter.Animal::class.simpleName}, got ${route::class}"
            )
        }
    }

    fun onViewEvent(event: ShelterAnimalEvents) {
        when (event) {

            is ShelterAnimalEvents.BirthDate.DateSelected -> {
                _birthDateState.update { it.copy(date = event.date) }
            }

            ShelterAnimalEvents.Breed.MenuDismissed -> {
                _breedState.update { it.copy(isExpended = false) }
            }

            is ShelterAnimalEvents.Breed.BreedSelected -> {
                _breedState.update {
                    it.copy(
                        isExpended = false,
                        selected = event.breed
                    )
                }
            }

            ShelterAnimalEvents.Breed.ClickedBreed -> {
                if (_typeState.value.selected == null) return
                _breedState.update { it.copy(isExpended = !it.isExpended) }
            }

            ShelterAnimalEvents.Gender.ClickedGender -> {
                _genderState.update { it.copy(isExpended = !it.isExpended) }
            }

            ShelterAnimalEvents.Gender.MenuDismissed -> {
                _genderState.update { it.copy(isExpended = false) }
            }

            is ShelterAnimalEvents.Gender.GenderSelected -> {
                _genderState.update {
                    it.copy(
                        isExpended = false,
                        selected = event.gender
                    )
                }
            }

            ShelterAnimalEvents.Type.ClickedType -> {
                _typeState.update { it.copy(isExpended = !it.isExpended) }
            }

            ShelterAnimalEvents.Type.MenuDismissed -> {
                _typeState.update { it.copy(isExpended = false) }
            }

            is ShelterAnimalEvents.Type.TypeSelected -> {
                _typeState.update {
                    it.copy(
                        isExpended = false,
                        selected = event.type
                    )
                }
                _breedState.update {
                    it.copy(
                        selected = null,
                        options = AnimalUtils.getBreedForAnimalType(event.type) ?: emptySet(),
                        isExpended = false,
                        isValid = true
                    )
                }
            }

            is ShelterAnimalEvents.Description.TextUpdated ->
                _descriptionState.update { it.copy(text = event.newText) }

            is ShelterAnimalEvents.Name.TextUpdated ->
                _nameState.update { it.copy(text = event.newText) }

            ShelterAnimalEvents.CancelClicked -> {
                mainViewModel.navigate(
                    To(
                        NavigationRoute.ShelterHome,
                        To.PopUpType.Origin
                    )
                )
            }

            is ShelterAnimalEvents.Photos.AddAnimalPhotos -> {
                addAnimalPhoto(event.uri)
            }

            is ShelterAnimalEvents.Photos.AddPassportAnimalPhotos -> {
                addPassportPhoto(event.uri)
            }

            is ShelterAnimalEvents.Photos.RemoveAnimalPhotos -> {
                removeAnimalPhoto(event.uri)
            }

            is ShelterAnimalEvents.Photos.RemovePassportAnimalPhotos -> {
                removePassportPhoto(event.uri)
            }

            ShelterAnimalEvents.SaveChangeClicked -> {
                save()
            }

        }
    }

    private fun save() {

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

        _birthDateState.update {
            it.copy(isValid = isBirthDateValid)
        }

        val isAllValid = _nameState.value.validation is ValidationState.Valid &&
                _descriptionState.value.validation is ValidationState.Valid &&
                _typeState.value.isValid && _breedState.value.isValid &&
                _genderState.value.isValid && isDescriptionValid && isBirthDateValid

        if (isAllValid) {
            launchSave(
                operation = {
                    when (val currentMode = _mode.value) {
                        ShelterAnimalState.Mode.Add -> {
                            _shelterRepository.createAnimal(
                                name = trimmedName,
                                type = _typeState.value.selected!!,
                                breed = _breedState.value.selected!!,
                                gender = _genderState.value.selected!!,
                                description = trimmedDescription,
                                birthDate = birthDate,
                                animalPhotoUris = _animalPhotosState.value.animal,
                                passportPhotoUris = _animalPhotosState.value.passport,
                            )
                        }

                        is ShelterAnimalState.Mode.Edit -> {
                            val removedPhotos: List<String> =
                                currentMode.originalAnimalPhotos.filepath.filter { filepath: String ->
                                    !_animalPhotosState.value.animal.contains(filepath)
                                }.mapIndexed { index: Int, filepath: String ->
                                    currentMode.originalAnimalPhotos.filenames[index]
                                }

                            val newPhotoUris =
                                _animalPhotosState.value.animal.filter { filepath: String ->
                                    !currentMode.originalAnimalPhotos.filepath.contains(filepath)
                                }

                            Log.i(
                                "shelter animal",
                                "original photo ${currentMode.originalAnimalPhotos}, " +
                                        "state: ${animalPhotosState.value.animal}, removed $removedPhotos, new $newPhotoUris"
                            )

                            val removedPassportPhotos: List<String> =
                                currentMode.originalPassportPhotos.filepath.filter { filepath: String ->
                                    !_animalPhotosState.value.passport.contains(filepath)
                                }.mapIndexed { index: Int, filepath: String ->
                                    currentMode.originalPassportPhotos.filenames[index]
                                }

                            val newPassportPhotoUris =
                                _animalPhotosState.value.passport.filter { filepath: String ->
                                    !currentMode.originalPassportPhotos.filepath.contains(filepath)
                                }

                            Log.i(
                                "shelter animal",
                                "original passport photo ${currentMode.originalPassportPhotos}, " +
                                        "state: ${animalPhotosState.value.passport}, removed $removedPassportPhotos, new $newPassportPhotoUris"
                            )

                            _shelterRepository.updateAnimal(
                                id = currentMode.idAnimal,
                                name = trimmedName,
                                type = _typeState.value.selected!!,
                                breed = _breedState.value.selected!!,
                                gender = _genderState.value.selected!!,
                                description = trimmedDescription,
                                birthDate = birthDate,
                                removedAnimalPhotos = removedPhotos,
                                newPhotoUris = newPhotoUris,
                                removedPassportPhotos = removedPassportPhotos,
                                newPassportUris = newPassportPhotoUris,
                            )
                        }
                    }

                },
                onSuccess = {
                    mainViewModel.navigate(
                        To(
                            NavigationRoute.ShelterHome,
                            To.PopUpType.Origin
                        )
                    )
                }
            )
        }

    }

    private fun addAnimalPhoto(uri: String) {
        val currentPhotos = _animalPhotosState.value.animal
        if (currentPhotos.size < MAX_ANIMAL_PHOTOS) {
            _animalPhotosState.update {
                it.copy(animal = currentPhotos + uri)
            }
        }
    }

    private fun addPassportPhoto(uri: String) {
        val currentPhotos = _animalPhotosState.value.passport
        if (currentPhotos.size < MAX_PASSPORT_ANIMAL_PHOTOS) {
            _animalPhotosState.update {
                it.copy(passport = currentPhotos + uri)
            }
        }
    }

    private fun removeAnimalPhoto(uri: String) {
        _animalPhotosState.update {
            it.copy(animal = it.animal.filter { photo -> photo != uri })
        }
    }

    private fun removePassportPhoto(uri: String) {
        _animalPhotosState.update {
            it.copy(passport = it.passport.filter { photo -> photo != uri })
        }
    }
}





