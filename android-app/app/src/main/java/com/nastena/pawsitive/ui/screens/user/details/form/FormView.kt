package com.nastena.pawsitive.ui.screens.user.details.form

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nastena.pawsitive.R
import com.nastena.pawsitive.ui.common.PawsitiveTextButton
import com.nastena.pawsitive.ui.common.validation.ValidationState
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

    var openDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = birthDateState.date
    )

    val formattedDate = birthDateState.date?.let {
        SimpleDateFormat("dd.MM.yyyy").format(Date(it))
    } ?: ""

    Column {

        OutlinedTextField(
            value = formattedDate,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(stringResource(R.string.add_animal_birth_date_label))
            },
            placeholder = {
                Text(stringResource(R.string.add_animal_birth_date_choose))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Cake,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { openDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null
                    )
                }
            },
            isError = !birthDateState.isValid
        )

        AnimatedVisibility(visible = !birthDateState.isValid) {
            Text(
                text = stringResource(R.string.add_animal_birth_date_error),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }

        if (openDialog) {
            DatePickerDialog(
                onDismissRequest = { openDialog = false },
                confirmButton = {
                    PawsitiveTextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let {
                                onDateSelected(it)
                            }
                            openDialog = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    PawsitiveTextButton(
                        onClick = { openDialog = false }
                    ) {
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
            .background(MaterialTheme.colorScheme.background)
            .imePadding()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .widthIn(max = 420.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ------------- Title --------------------
            Text(
                text = stringResource(R.string.form_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ------------- Animal and Shelter names --------------------
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = animalState.animalName,
                        modifier = Modifier.weight(1f),
                        softWrap = true
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = animalState.shelterName,
                        modifier = Modifier.weight(1f),
                        softWrap = true
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))


                // ------------- Full name --------------------

                OutlinedTextField(
                    value = fullNameState.text,
                    onValueChange = {
                        onViewEvent(FormEvents.FullName.TextUpdated(it))
                    },
                    label = { Text(stringResource(R.string.full_name_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Person, contentDescription = null)
                    },
                    singleLine = false,
                    isError = fullNameState.validation != ValidationState.Valid,
                    supportingText = {
                        when (fullNameState.validation) {
                            ValidationState.Empty ->
                                Text(stringResource(R.string.not_empty))

                            ValidationState.InvalidFormat ->
                                Text(stringResource(R.string.full_name_label_invalid))

                            else -> {}
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------- Birth date --------------------

                BirthDatePicker(
                    birthDateState = birthDateState,
                    onDateSelected = { selectedDate ->
                        onViewEvent(FormEvents.BirthDate.DateSelected(selectedDate))
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------- Profession --------------------
                OutlinedTextField(
                    value = professionState.text,
                    onValueChange = {
                        onViewEvent(FormEvents.Profession.TextUpdated(it))
                    },
                    label = { Text(stringResource(R.string.profession_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Work, contentDescription = null)
                    },
                    singleLine = false,
                    isError = professionState.validation != ValidationState.Valid,
                    supportingText = {
                        when (professionState.validation) {
                            ValidationState.Empty ->
                                Text(stringResource(R.string.not_empty))

                            ValidationState.InvalidFormat ->
                                Text(stringResource(R.string.required_field))

                            else -> {}
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------- Current pets --------------------
                OutlinedTextField(
                    value = currentPetsState.text,
                    onValueChange = {
                        onViewEvent(FormEvents.CurrentPets.TextUpdated(it))
                    },
                    label = { Text(stringResource(R.string.adoption_form_current_pets)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Pets, contentDescription = null)
                    },
                    singleLine = false,
                    isError = currentPetsState.validation != ValidationState.Valid,
                    supportingText = {
                        when (currentPetsState.validation) {
                            ValidationState.Empty ->
                                Text(stringResource(R.string.not_empty))

                            ValidationState.InvalidFormat ->
                                Text(stringResource(R.string.required_field))

                            else -> {}
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------- Previous pets --------------------
                OutlinedTextField(
                    value = previousPetsState.text,
                    onValueChange = {
                        onViewEvent(FormEvents.PreviousPets.TextUpdated(it))
                    },
                    label = { Text(stringResource(R.string.adoption_form_previous_pets)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.History, contentDescription = null)
                    },
                    singleLine = false,
                    isError = previousPetsState.validation != ValidationState.Valid,
                    supportingText = {
                        when (previousPetsState.validation) {
                            ValidationState.Empty ->
                                Text(stringResource(R.string.not_empty))

                            ValidationState.InvalidFormat ->
                                Text(stringResource(R.string.required_field))

                            else -> {}
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------- Feeding experience --------------------
                OutlinedTextField(
                    value = feedingExperienceState.text,
                    onValueChange = {
                        onViewEvent(FormEvents.FeedingExperience.TextUpdated(it))
                    },
                    label = { Text(stringResource(R.string.adoption_form_feeding_experience)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Restaurant, contentDescription = null)
                    },
                    singleLine = false,
                    isError = feedingExperienceState.validation != ValidationState.Valid,
                    supportingText = {
                        when (feedingExperienceState.validation) {
                            ValidationState.Empty ->
                                Text(stringResource(R.string.not_empty))

                            ValidationState.InvalidFormat ->
                                Text(stringResource(R.string.required_field))

                            else -> {}
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------- Vaccination --------------------
                OutlinedTextField(
                    value = vaccinationState.text,
                    onValueChange = {
                        onViewEvent(FormEvents.Vaccination.TextUpdated(it))
                    },
                    label = { Text(stringResource(R.string.adoption_form_vaccination)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Vaccines, contentDescription = null)
                    },
                    singleLine = false,
                    isError = vaccinationState.validation != ValidationState.Valid,
                    supportingText = {
                        when (vaccinationState.validation) {
                            ValidationState.Empty ->
                                Text(stringResource(R.string.not_empty))

                            ValidationState.InvalidFormat ->
                                Text(stringResource(R.string.required_field))

                            else -> {}
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------- Reason --------------------
                OutlinedTextField(
                    value = reasonState.text,
                    onValueChange = {
                        onViewEvent(FormEvents.Reason.TextUpdated(it))
                    },
                    label = { Text(stringResource(R.string.adoption_form_reason)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.QuestionAnswer, contentDescription = null)
                    },
                    singleLine = false,
                    isError = reasonState.validation != ValidationState.Valid,
                    supportingText = {
                        when (reasonState.validation) {
                            ValidationState.Empty ->
                                Text(stringResource(R.string.not_empty))

                            ValidationState.InvalidFormat ->
                                Text(stringResource(R.string.required_field))

                            else -> {}
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------- Pet Care When Away --------------------
                OutlinedTextField(
                    value = petCareWhenAwayState.text,
                    onValueChange = {
                        onViewEvent(FormEvents.PetCareWhenAway.TextUpdated(it))
                    },
                    label = { Text(stringResource(R.string.adoption_form_pet_care_when_away)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Home, contentDescription = null)
                    },
                    singleLine = false,
                    isError = petCareWhenAwayState.validation != ValidationState.Valid,
                    supportingText = {
                        when (petCareWhenAwayState.validation) {
                            ValidationState.Empty ->
                                Text(stringResource(R.string.not_empty))

                            ValidationState.InvalidFormat ->
                                Text(stringResource(R.string.required_field))

                            else -> {}
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------- Problem Character --------------------
                OutlinedTextField(
                    value = problemCharacterState.text,
                    onValueChange = {
                        onViewEvent(FormEvents.ProblemCharacter.TextUpdated(it))
                    },
                    label = { Text(stringResource(R.string.adoption_form_problem_character)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Warning, contentDescription = null)
                    },
                    singleLine = false,
                    isError = problemCharacterState.validation != ValidationState.Valid,
                    supportingText = {
                        when (problemCharacterState.validation) {
                            ValidationState.Empty ->
                                Text(stringResource(R.string.not_empty))

                            ValidationState.InvalidFormat ->
                                Text(stringResource(R.string.required_field))

                            else -> {}
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------- Health Issues --------------------
                OutlinedTextField(
                    value = healthIssuesState.text,
                    onValueChange = {
                        onViewEvent(FormEvents.HealthIssues.TextUpdated(it))
                    },
                    label = { Text(stringResource(R.string.adoption_form_health_issues)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.HealthAndSafety, contentDescription = null)
                    },
                    singleLine = false,
                    isError = healthIssuesState.validation != ValidationState.Valid,
                    supportingText = {
                        when (healthIssuesState.validation) {
                            ValidationState.Empty ->
                                Text(stringResource(R.string.not_empty))

                            ValidationState.InvalidFormat ->
                                Text(stringResource(R.string.required_field))

                            else -> {}
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------- Additional info --------------------
                OutlinedTextField(
                    value = additionalInfoState.text,
                    onValueChange = { newText ->
                        onViewEvent(FormEvents.AdditionalInfo.TextUpdated(newText))
                    },
                    label = { Text(stringResource(R.string.adoption_form_additional_info)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Info, contentDescription = null)
                    },
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ------------- Phone --------------------
                OutlinedTextField(
                    value = phoneState.text,
                    onValueChange = {
                        onViewEvent(FormEvents.Phone.TextUpdated(it))
                    },
                    label = { Text(stringResource(R.string.phone_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Phone, contentDescription = null)
                    },
                    singleLine = true,
                    isError = phoneState.validation != ValidationState.Valid,
                    supportingText = {
                        when (phoneState.validation) {
                            ValidationState.Empty ->
                                Text(stringResource(R.string.not_empty))

                            ValidationState.InvalidFormat ->
                                Text(stringResource(R.string.required_field))

                            else -> {}
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

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
}