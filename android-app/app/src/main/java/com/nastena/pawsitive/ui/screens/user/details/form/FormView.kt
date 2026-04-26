package com.nastena.pawsitive.ui.screens.user.details.form

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nastena.pawsitive.R
import com.nastena.pawsitive.ui.common.validation.ValidationState
import com.nastena.pawsitive.ui.screens.shelter.editing.PhoneVisualTransformation
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun FormView(
    modifier: Modifier = Modifier,
    viewModel: FormViewModel
) {
    // ------------- States --------------------
    val animalState: FormState.AnimalInfo by viewModel.animalState.collectAsState()
    val fullNameState: FormState.FullName by viewModel.fullNameState.collectAsState()
    val birthDateState: FormState.BirthDate by viewModel.birthDateState.collectAsState()
    val professionState: FormState.Profession by viewModel.professionState.collectAsState()

    val currentPetsState: FormState.CurrentPets by viewModel.currentPetsState.collectAsState()
    val previousPetsState: FormState.PreviousPets by viewModel.previousPetsState.collectAsState()
    val feedingExperienceState: FormState.FeedingExperience by viewModel.feedingExperienceState.collectAsState()
    val vaccinationState: FormState.Vaccination by viewModel.vaccinationState.collectAsState()
    val reasonState: FormState.Reason by viewModel.reasonState.collectAsState()
    val petCareWhenAwayState: FormState.PetCareWhenAway by viewModel.petCareWhenAwayState.collectAsState()
    val problemCharacterState: FormState.ProblemCharacter by viewModel.problemCharacterState.collectAsState()
    val healthIssuesState: FormState.HealthIssues by viewModel.healthIssuesState.collectAsState()
    val additionalInfoState: FormState.AdditionalInfo by viewModel.additionalInfoState.collectAsState()

    val phoneState: FormState.Phone by viewModel.phoneState.collectAsState()

    FormView(
        modifier = modifier,
        animalState = animalState,
        fullNameState = fullNameState,
        birthDateState = birthDateState,
        professionState = professionState,

        currentPetsState = currentPetsState,
        previousPetsState = previousPetsState,
        feedingExperienceState = feedingExperienceState,
        vaccinationState = vaccinationState,
        reasonState = reasonState,
        petCareWhenAwayState = petCareWhenAwayState,
        problemCharacterState = problemCharacterState,
        healthIssuesState = healthIssuesState,
        additionalInfoState = additionalInfoState,
        phoneState = phoneState,
        onViewEvent = { event: FormEvents -> viewModel.onViewEvent(event) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BirthDatePicker(
    birthDateState: FormState.BirthDate,
    onDateSelected: (Long) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = birthDateState.date
    )

    Column {
        AnimatedVisibility(visible = !birthDateState.isValid) {
            OutlinedTextField(
                value = "Выберите корректную дату рождения",
                onValueChange = {},
                readOnly = true,
                isError = true
            )
        }

        OutlinedButton(
            onClick = { openDialog.value = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = birthDateState.date?.let {
                    SimpleDateFormat("dd.MM.yyyy").format(Date(it))
                } ?: "Выберите дату рождения"
            )
        }

        if (openDialog.value) {
            DatePickerDialog(
                onDismissRequest = { openDialog.value = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { selected ->
                            onDateSelected(selected)
                        }
                        openDialog.value = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { openDialog.value = false }) {
                        Text("Отмена")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@Composable
private fun FormView(
    modifier: Modifier = Modifier,
    animalState: FormState.AnimalInfo,
    fullNameState: FormState.FullName,
    birthDateState: FormState.BirthDate,
    professionState: FormState.Profession,

    currentPetsState: FormState.CurrentPets,
    previousPetsState: FormState.PreviousPets,
    feedingExperienceState: FormState.FeedingExperience,
    vaccinationState: FormState.Vaccination,
    reasonState: FormState.Reason,
    petCareWhenAwayState: FormState.PetCareWhenAway,
    problemCharacterState: FormState.ProblemCharacter,
    healthIssuesState: FormState.HealthIssues,
    additionalInfoState: FormState.AdditionalInfo,

    phoneState: FormState.Phone,
    onViewEvent: (FormEvents) -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ------------- Title --------------------
            Text(
                text = stringResource(R.string.form_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ------------- Animal name --------------------

            Text(text = animalState.animalName)

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = animalState.shelterName)

            // ------------- Full name --------------------
            AnimatedVisibility(
                visible = fullNameState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (fullNameState.validation) {
                        ValidationState.Empty -> stringResource(R.string.not_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.required_field)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = fullNameState.text,
                onValueChange = { newText ->
                    onViewEvent(FormEvents.FullName.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.full_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = fullNameState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Birth date --------------------

            BirthDatePicker(
                birthDateState = birthDateState,
                onDateSelected = { selectedDate ->
                    onViewEvent(FormEvents.BirthDate.DateSelected(selectedDate))
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Profession --------------------
            AnimatedVisibility(
                visible = professionState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (professionState.validation) {
                        ValidationState.Empty -> stringResource(R.string.not_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.required_field)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = professionState.text,
                onValueChange = { newText ->
                    onViewEvent(FormEvents.Profession.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.profession_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = professionState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Current pets --------------------
            AnimatedVisibility(
                visible = currentPetsState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (currentPetsState.validation) {
                        ValidationState.Empty -> stringResource(R.string.not_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.required_field)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = currentPetsState.text,
                onValueChange = { newText ->
                    onViewEvent(FormEvents.CurrentPets.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.adoption_form_current_pets)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = currentPetsState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Previous pets --------------------
            AnimatedVisibility(
                visible = previousPetsState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (previousPetsState.validation) {
                        ValidationState.Empty -> stringResource(R.string.not_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.required_field)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = previousPetsState.text,
                onValueChange = { newText ->
                    onViewEvent(FormEvents.PreviousPets.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.adoption_form_previous_pets)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = previousPetsState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Feeding experience --------------------
            AnimatedVisibility(
                visible = feedingExperienceState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (feedingExperienceState.validation) {
                        ValidationState.Empty -> stringResource(R.string.not_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.required_field)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = feedingExperienceState.text,
                onValueChange = { newText ->
                    onViewEvent(FormEvents.FeedingExperience.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.adoption_form_feeding_experience)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = feedingExperienceState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Vaccination --------------------
            AnimatedVisibility(
                visible = vaccinationState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (vaccinationState.validation) {
                        ValidationState.Empty -> stringResource(R.string.not_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.required_field)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = vaccinationState.text,
                onValueChange = { newText ->
                    onViewEvent(FormEvents.Vaccination.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.adoption_form_vaccination)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = vaccinationState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Reason --------------------
            AnimatedVisibility(
                visible = reasonState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (reasonState.validation) {
                        ValidationState.Empty -> stringResource(R.string.not_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.required_field)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = reasonState.text,
                onValueChange = { newText ->
                    onViewEvent(FormEvents.Reason.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.adoption_form_reason)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = reasonState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Pet Care When Away --------------------
            AnimatedVisibility(
                visible = petCareWhenAwayState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (petCareWhenAwayState.validation) {
                        ValidationState.Empty -> stringResource(R.string.not_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.required_field)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = petCareWhenAwayState.text,
                onValueChange = { newText ->
                    onViewEvent(FormEvents.PetCareWhenAway.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.adoption_form_pet_care_when_away)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = petCareWhenAwayState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Problem Character --------------------
            AnimatedVisibility(
                visible = problemCharacterState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (problemCharacterState.validation) {
                        ValidationState.Empty -> stringResource(R.string.not_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.required_field)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = problemCharacterState.text,
                onValueChange = { newText ->
                    onViewEvent(FormEvents.ProblemCharacter.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.adoption_form_problem_character)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = problemCharacterState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Health Issues --------------------
            AnimatedVisibility(
                visible = healthIssuesState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (healthIssuesState.validation) {
                        ValidationState.Empty -> stringResource(R.string.not_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.required_field)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = healthIssuesState.text,
                onValueChange = { newText ->
                    onViewEvent(FormEvents.HealthIssues.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.adoption_form_health_issues)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = healthIssuesState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Additional info --------------------
            AnimatedVisibility(
                visible = additionalInfoState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (additionalInfoState.validation) {
                        ValidationState.Empty -> stringResource(R.string.not_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.required_field)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = additionalInfoState.text,
                onValueChange = { newText ->
                    onViewEvent(FormEvents.AdditionalInfo.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.adoption_form_additional_info)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = additionalInfoState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Phone --------------------
            AnimatedVisibility(
                visible = phoneState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (phoneState.validation) {
                        ValidationState.Empty -> stringResource(R.string.not_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.phone_invalid)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = phoneState.text,
                onValueChange = { newText ->
                    val digits = newText.filter { it.isDigit() }.take(10)
                    onViewEvent(FormEvents.Phone.TextUpdated(digits))
                },
                label = { Text(stringResource(R.string.phone_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = phoneState.validation != ValidationState.Valid,
                visualTransformation = PhoneVisualTransformation("+7-000-000-00-00", '0')
            )
            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Button --------------------

            Button(
                onClick = { onViewEvent(FormEvents.SendForm) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.form_send_button))
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}