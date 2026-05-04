package com.nastena.pawsitive.ui.screens.user.details.form

import android.util.Log
import com.nastena.pawsitive.R
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.common.navigation.Navigation
import com.nastena.pawsitive.ui.common.navigation.NavigationRoute
import com.nastena.pawsitive.ui.common.validation.ValidationState
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.BaseScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern
import kotlin.reflect.KClass

class FormViewModel(
    mainViewModel: MainViewModel,
    private val _userRepository: UserRepository
) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.Form::class

    companion object {
        private val NAME_REGEX = Pattern.compile("^[А-Яа-я\\s]{2,300}$")

        private val PHONE_REGEX = Pattern.compile(
            "^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$"
        )
    }

    private val _animalState: MutableStateFlow<FormState.AnimalInfo> = MutableStateFlow(
        FormState.AnimalInfo(
            animalName = "",
            shelterName = ""
        )
    )

    val animalState: StateFlow<FormState.AnimalInfo> = _animalState.asStateFlow()

    private val _fullNameState = MutableStateFlow(
        FormState.FullName(
            text = "", validation = ValidationState.Valid
        )
    )
    val fullNameState: StateFlow<FormState.FullName> = _fullNameState.asStateFlow()

    private val _birthDateState =
        MutableStateFlow(FormState.BirthDate(date = null, isValid = true))

    val birthDateState: StateFlow<FormState.BirthDate> = _birthDateState.asStateFlow()

    private val _professionState = MutableStateFlow(
        FormState.Profession(
            text = "", validation = ValidationState.Valid
        )
    )
    val professionState: StateFlow<FormState.Profession> = _professionState.asStateFlow()

    private val _currentPetsState = MutableStateFlow(
        FormState.CurrentPets(
            text = "", validation = ValidationState.Valid
        )
    )
    val currentPetsState: StateFlow<FormState.CurrentPets> = _currentPetsState.asStateFlow()

    private val _previousPetsState = MutableStateFlow(
        FormState.PreviousPets(
            text = "", validation = ValidationState.Valid
        )
    )
    val previousPetsState: StateFlow<FormState.PreviousPets> = _previousPetsState.asStateFlow()

    private val _feedingExperienceState = MutableStateFlow(
        FormState.FeedingExperience(
            text = "", validation = ValidationState.Valid
        )
    )
    val feedingExperienceState: StateFlow<FormState.FeedingExperience> =
        _feedingExperienceState.asStateFlow()

    private val _vaccinationState = MutableStateFlow(
        FormState.Vaccination(
            text = "", validation = ValidationState.Valid
        )
    )
    val vaccinationState: StateFlow<FormState.Vaccination> = _vaccinationState.asStateFlow()

    private val _reasonState = MutableStateFlow(
        FormState.Reason(
            text = "", validation = ValidationState.Valid
        )
    )
    val reasonState: StateFlow<FormState.Reason> = _reasonState.asStateFlow()

    private val _petCareWhenAwayState = MutableStateFlow(
        FormState.PetCareWhenAway(
            text = "", validation = ValidationState.Valid
        )
    )
    val petCareWhenAwayState: StateFlow<FormState.PetCareWhenAway> =
        _petCareWhenAwayState.asStateFlow()

    private val _problemCharacterState = MutableStateFlow(
        FormState.ProblemCharacter(
            text = "", validation = ValidationState.Valid
        )
    )
    val problemCharacterState: StateFlow<FormState.ProblemCharacter> =
        _problemCharacterState.asStateFlow()

    private val _healthIssuesState = MutableStateFlow(
        FormState.HealthIssues(
            text = "", validation = ValidationState.Valid
        )
    )
    val healthIssuesState: StateFlow<FormState.HealthIssues> = _healthIssuesState.asStateFlow()

    private val _additionalInfoState = MutableStateFlow(
        FormState.AdditionalInfo(
            text = "",
            validation = ValidationState.Valid
        )
    )
    val additionalInfoState: StateFlow<FormState.AdditionalInfo> =
        _additionalInfoState.asStateFlow()

    private val _phoneState = MutableStateFlow(
        FormState.Phone(
            text = "", validation = ValidationState.Valid
        )
    )
    val phoneState: StateFlow<FormState.Phone> = _phoneState.asStateFlow()

    private var _isUserFormChanged = false
    private var _animalId: Long = 0

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        mainViewModel.hideNavigationBar()

        _isUserFormChanged = false

        val formRoute = route as NavigationRoute.Form
        _animalId = formRoute.animalId

        Log.d("FormViewModel", "AnimalId received: $_animalId")


        launchSave(
            operation = {
                _userRepository.getFormForAnimal(formRoute.animalId)
            },
            onSuccess = { response ->
                _animalState.update {
                    it.copy(
                        animalName = response.animalName,
                        shelterName = response.shelterName
                    )
                }

                _fullNameState.update {
                    it.copy(
                        text = response.name,
                        validation = ValidationState.Valid
                    )
                }

                _birthDateState.update { it.copy(response.birthDate, isValid = true) }

                _professionState.update {
                    it.copy(
                        text = response.profession,
                        validation = ValidationState.Valid
                    )
                }

                _currentPetsState.update {
                    it.copy(
                        text = response.currentPets,
                        validation = ValidationState.Valid
                    )
                }

                _previousPetsState.update {
                    it.copy(
                        text = response.previousPets,
                        validation = ValidationState.Valid
                    )
                }

                _feedingExperienceState.update {
                    it.copy(
                        text = response.feedingExperience,
                        validation = ValidationState.Valid
                    )
                }

                _vaccinationState.update {
                    it.copy(
                        text = response.vaccination,
                        validation = ValidationState.Valid
                    )
                }

                _reasonState.update {
                    it.copy(
                        text = response.reason,
                        validation = ValidationState.Valid
                    )
                }

                _petCareWhenAwayState.update {
                    it.copy(
                        text = response.petCareWhenAway,
                        validation = ValidationState.Valid
                    )
                }

                _problemCharacterState.update {
                    it.copy(
                        text = response.problemCharacter,
                        validation = ValidationState.Valid
                    )
                }

                _healthIssuesState.update {
                    it.copy(
                        text = response.healthIssues,
                        validation = ValidationState.Valid
                    )
                }

                _additionalInfoState.update {
                    it.copy(
                        text = response.additionalInfo
                    )
                }


                _phoneState.update {
                    it.copy(
                        text = response.phone ?: "",
                        validation = ValidationState.Valid
                    )
                }

            }
        )
    }

    override fun onExit() {
        mainViewModel.showNavigationBar()

        super.onExit()
    }

    fun onViewEvent(event: FormEvents) {
        when (event) {
            is FormEvents.BirthDate.DateSelected -> {
                _birthDateState.update { it.copy(date = event.date) }
                _isUserFormChanged = true
            }

            is FormEvents.FullName.TextUpdated -> {
                _fullNameState.update { it.copy(text = event.newText) }
                _isUserFormChanged = true
            }

            is FormEvents.Phone.TextUpdated -> {
                _phoneState.update { it.copy(text = event.newText) }
                _isUserFormChanged = true
            }

            is FormEvents.Profession.TextUpdated -> {
                _professionState.update { it.copy(text = event.newText) }
                _isUserFormChanged = true
            }


            is FormEvents.AdditionalInfo.TextUpdated -> {
                _additionalInfoState.update { it.copy(text = event.newText) }
                _isUserFormChanged = true
            }

            is FormEvents.CurrentPets.TextUpdated -> {
                _currentPetsState.update { it.copy(text = event.newText) }
                _isUserFormChanged = true
            }

            is FormEvents.FeedingExperience.TextUpdated -> {
                _feedingExperienceState.update { it.copy(text = event.newText) }
                _isUserFormChanged = true
            }

            is FormEvents.HealthIssues.TextUpdated -> {
                _healthIssuesState.update { it.copy(text = event.newText) }
                _isUserFormChanged = true
            }

            is FormEvents.PetCareWhenAway.TextUpdated -> {
                _petCareWhenAwayState.update { it.copy(text = event.newText) }
                _isUserFormChanged = true
            }

            is FormEvents.PreviousPets.TextUpdated -> {
                _previousPetsState.update { it.copy(text = event.newText) }
                _isUserFormChanged = true
            }

            is FormEvents.ProblemCharacter.TextUpdated -> {
                _problemCharacterState.update { it.copy(text = event.newText) }
                _isUserFormChanged = true
            }

            is FormEvents.Reason.TextUpdated -> {
                _reasonState.update { it.copy(text = event.newText) }
                _isUserFormChanged = true
            }

            is FormEvents.Vaccination.TextUpdated -> {
                _vaccinationState.update { it.copy(text = event.newText) }
                _isUserFormChanged = true
            }

            is FormEvents.SendForm -> {
                sendForm()
            }

        }
    }

    private fun sendForm() {

        Log.d("FormViewModel", "Send form pressed")

        val birthDate = _birthDateState.value.date
        val trimmedName = _fullNameState.value.text.trim()
        val trimmedProfession = _professionState.value.text.trim()

        val trimmedCurrentPets = _currentPetsState.value.text.trim()
        val trimmedPreviousPets = _previousPetsState.value.text.trim()
        val trimmedFeedingExperience = _feedingExperienceState.value.text.trim()
        val trimmedVaccination = _vaccinationState.value.text.trim()
        val trimmedReason = _reasonState.value.text.trim()
        val trimmedPetCareWhenAway = _petCareWhenAwayState.value.text.trim()
        val trimmedProblemCharacter = _problemCharacterState.value.text.trim()
        val trimmedHealthIssues = _healthIssuesState.value.text.trim()
        val trimmedAdditionalInfo = _additionalInfoState.value.text.trim()

        val trimmedPhone = _phoneState.value.text.trim()

        // -------- BirthDate validation --------
        val isBirthDateValid = birthDate != null && birthDate < System.currentTimeMillis()

        _birthDateState.update {
            it.copy(isValid = isBirthDateValid)
        }

        if (!isBirthDateValid) return


        // -------- Name validation --------
        when {
            trimmedName.isBlank() -> {
                _fullNameState.update { it.copy(validation = ValidationState.Empty) }
                return
            }

            trimmedName.length < 2 ||
                    trimmedName.length > 300 ||
                    !NAME_REGEX.matcher(trimmedName).matches() -> {

                _fullNameState.update {
                    it.copy(validation = ValidationState.InvalidFormat)
                }
                return
            }

            else -> {
                _fullNameState.update { it.copy(validation = ValidationState.Valid) }
            }
        }

        // -------- Profession validation --------
        if (trimmedProfession.isBlank()) {
            _professionState.update {
                it.copy(validation = ValidationState.Empty)
            }
            return
        } else {
            _professionState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }

        // -------- Current Pets validation --------
        if (trimmedCurrentPets.isBlank()) {
            _currentPetsState.update {
                it.copy(validation = ValidationState.Empty)
            }
            return
        } else {
            _currentPetsState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }

        // -------- Previous Pets validation --------
        if (trimmedPreviousPets.isBlank()) {
            _previousPetsState.update {
                it.copy(validation = ValidationState.Empty)
            }
            return
        } else {
            _previousPetsState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }

        // -------- Feeding Experience validation --------
        if (trimmedFeedingExperience.isBlank()) {
            _feedingExperienceState.update {
                it.copy(validation = ValidationState.Empty)
            }
            return
        } else {
            _feedingExperienceState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }

        // -------- Vaccination validation --------
        if (trimmedVaccination.isBlank()) {
            _vaccinationState.update {
                it.copy(validation = ValidationState.Empty)
            }
            return
        } else {
            _vaccinationState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }

        // -------- Reason validation --------
        if (trimmedReason.isBlank()) {
            _reasonState.update {
                it.copy(validation = ValidationState.Empty)
            }
            return
        } else {
            _reasonState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }

        // -------- Pet Care When Away validation --------
        if (trimmedPetCareWhenAway.isBlank()) {
            _petCareWhenAwayState.update {
                it.copy(validation = ValidationState.Empty)
            }
            return
        } else {
            _petCareWhenAwayState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }

        // -------- Problem Character validation --------
        if (trimmedProblemCharacter.isBlank()) {
            _problemCharacterState.update {
                it.copy(validation = ValidationState.Empty)
            }
            return
        } else {
            _problemCharacterState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }

        // -------- Health Issues validation --------
        if (trimmedHealthIssues.isBlank()) {
            _healthIssuesState.update {
                it.copy(validation = ValidationState.Empty)
            }
            return
        } else {
            _healthIssuesState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }


        // -------- Phone validation --------
        if (!PHONE_REGEX.matcher(trimmedPhone).matches()) {
            _phoneState.update { it.copy(validation = ValidationState.InvalidFormat) }
            return
        } else {
            _phoneState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }


        // -------- Send form --------
        if (_isUserFormChanged) {

            Log.d("FormViewModel", "User form changed -> updating form")

            launchSave(
                operation = {
                    _userRepository.updateForm(
                        trimmedName,
                        birthDate,
                        trimmedProfession,
                        trimmedCurrentPets,
                        trimmedPreviousPets,
                        trimmedFeedingExperience,
                        trimmedVaccination,
                        trimmedReason,
                        trimmedPetCareWhenAway,
                        trimmedProblemCharacter,
                        trimmedHealthIssues,
                        trimmedAdditionalInfo,
                        trimmedPhone
                    )
                },
                onSuccess = {
                    createForm()
                }
            )

        } else {
            createForm()
        }
    }

    private fun createForm() {

        Log.d("FormViewModel", "Creating adoption form for animalId=$_animalId")

        launchSave(
            operation = {
                _userRepository.createForm(_animalId)
                _userRepository.getFavorites()
            },
            onSuccess = {

                Log.d("FormViewModel", "Form successfully created")

                mainViewModel.showMessage(
                    R.string.form_sent,
                    onOkay = {
                        mainViewModel.navigate(
                            Navigation.To(
                                NavigationRoute.UserHome,
                                popUpType = Navigation.To.PopUpType.Origin
                            )
                        )
                    }
                )
            }
        )
    }
}


