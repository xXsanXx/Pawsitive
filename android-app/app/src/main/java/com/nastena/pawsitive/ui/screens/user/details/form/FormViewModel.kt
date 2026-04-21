package com.nastena.pawsitive.ui.screens.user.details.form

import android.util.Log
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

        _isUserFormChanged = false

        mainViewModel.hideNavigationBar()

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

                _phoneState.update {
                    it.copy(
                        text = response.phone.removePrefix("+7"),
                        validation = ValidationState.Valid
                    )
                }

            }
        )
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

            is FormEvents.SendForm -> {
                sendForm(event.messageIdOnSuccess)
            }
        }
    }

    private fun sendForm(messageIdOnSuccess: Int) {

        Log.d("FormViewModel", "Send form pressed")

        val birthDate = _birthDateState.value.date
        val trimmedName = _fullNameState.value.text.trim()
        val trimmedProfession = _professionState.value.text.trim()
        val trimmedPhone = "+7${_phoneState.value.text.trim()}"

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


        // -------- Phone validation --------
        if (!PHONE_REGEX.matcher(trimmedPhone).matches()) {
            _phoneState.update {
                it.copy(validation = ValidationState.InvalidFormat)
            }
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
                        trimmedPhone
                    )
                },
                onSuccess = {
                    createForm(messageIdOnSuccess)
                }
            )

        } else {
            createForm(messageIdOnSuccess)
        }
    }

    private fun createForm(messageIdOnSuccess: Int) {

        Log.d("FormViewModel", "Creating adoption form for animalId=$_animalId")

        launchSave(
            operation = {
                _userRepository.createForm(_animalId)
                _userRepository.getFavorites()
            },
            onSuccess = {

                Log.d("FormViewModel", "Form successfully created")

                mainViewModel.showMessage(
                    messageIdOnSuccess,
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


