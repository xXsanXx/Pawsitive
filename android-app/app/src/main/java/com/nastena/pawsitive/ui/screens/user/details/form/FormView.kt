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
    val phoneState: FormState.Phone by viewModel.phoneState.collectAsState()

    FormView(
        modifier = modifier,
        animalState = animalState,
        fullNameState = fullNameState,
        birthDateState = birthDateState,
        professionState = professionState,
        phoneState = phoneState,
        onViewEvent = { event -> viewModel.onViewEvent(event) }
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
                        ValidationState.Empty -> stringResource(R.string.full_name_is_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.full_name_invalid)
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
                        ValidationState.Empty -> stringResource(R.string.profession_is_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.profession_invalid)
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

            // ------------- Phone --------------------
            AnimatedVisibility(
                visible = phoneState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (phoneState.validation) {
                        ValidationState.Empty -> stringResource(R.string.phone_is_empty)
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
                    onViewEvent(FormEvents.Phone.TextUpdated("+7$digits"))
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
                onClick = { onViewEvent(FormEvents.SendForm(R.string.form_send)) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.form_send_button))
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}