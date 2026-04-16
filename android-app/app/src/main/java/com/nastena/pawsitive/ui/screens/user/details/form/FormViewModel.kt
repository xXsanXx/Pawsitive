package com.nastena.pawsitive.ui.screens.user.details.form

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
) : BaseScreenViewModel(mainViewModel) {

    override val expectedRouteType: KClass<*> = NavigationRoute.Form::class

    companion object {
        private val NAME_REGEX = Pattern.compile("^[А-Яа-я\\s]{2,200}$")

        private val PHONE_REGEX = Pattern.compile(
            "^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$"
        )
    }

    private val _animalName = MutableStateFlow(
        FormState.AnimalName(
            text = "", validation = ValidationState.Valid
        )
    )
    val animalName: StateFlow<FormState.AnimalName> = _animalName.asStateFlow()

    private val _fullNameState = MutableStateFlow(
        FormState.FullName(
            text = "", validation = ValidationState.Valid
        )
    )
    val fullNameState: StateFlow<FormState.FullName> = _fullNameState.asStateFlow()

    private val _ageState = MutableStateFlow(
        FormState.Age(
            text = "", validation = ValidationState.Valid
        )
    )
    val ageState: StateFlow<FormState.Age> = _ageState.asStateFlow()

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

    override fun onEnter(route: NavigationRoute) {
        super.onEnter(route)

        mainViewModel.hideNavigationBar()

        _animalName.update { it.copy(text = "", validation = ValidationState.Valid) }

        _fullNameState.update { it.copy(text = "", validation = ValidationState.Valid) }

        _ageState.update { it.copy(text = "", validation = ValidationState.Valid) }

        _professionState.update { it.copy(text = "", validation = ValidationState.Valid) }

        _phoneState.update { it.copy(text = "", validation = ValidationState.Valid) }
    }

    fun onViewEvent(event: FormEvents) {
        when (event) {
            is FormEvents.Age.TextUpdated -> {
                _ageState.update { it.copy(text = event.newText) }
            }

            is FormEvents.AnimalName.TextUpdated -> {
                _animalName.update { it.copy(text = event.newText) }
            }

            is FormEvents.FullName.TextUpdated -> {
                _fullNameState.update { it.copy(text = event.newText) }
            }

            is FormEvents.Phone.TextUpdated -> {
                _phoneState.update { it.copy(text = event.newText) }
            }

            is FormEvents.Profession.TextUpdated -> {
                _professionState.update { it.copy(text = event.newText) }
            }

            FormEvents.SendForm -> {
                sendForm()
            }
        }
    }

    private fun sendForm() {
        val name = _fullNameState.value.text
        if (name.isBlank()) {
            _fullNameState.update { it.copy(validation = ValidationState.Empty) }
        } else if (
            name.length < 2 ||
            name.length > 50 ||
            !NAME_REGEX.matcher(name).matches()
        ) {
            _fullNameState.update { it.copy(validation = ValidationState.InvalidFormat) }
        } else {
            _fullNameState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }

        val trimmedAnimalName = _animalName.value.text.trim()
        if (trimmedAnimalName.isBlank()) {
            _animalName.update { it.copy(validation = ValidationState.Empty) }
        } else if (
            trimmedAnimalName.length < 2 ||
            trimmedAnimalName.length > 50 ||
            !NAME_REGEX.matcher(trimmedAnimalName).matches()
        ) {
            _animalName.update { it.copy(validation = ValidationState.InvalidFormat) }
        } else {
            _animalName.update {
                it.copy(validation = ValidationState.Valid)
            }
        }

        val trimmedPhone = _phoneState.value.text.trim()
        if (!PHONE_REGEX.matcher(trimmedPhone).matches()) {
            _phoneState.update { it.copy(validation = ValidationState.InvalidFormat) }
            return
        } else {
            _phoneState.update {
                it.copy(validation = ValidationState.Valid)
            }
        }


    }


}


