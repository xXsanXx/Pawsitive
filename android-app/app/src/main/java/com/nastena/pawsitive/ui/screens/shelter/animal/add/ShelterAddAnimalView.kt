package com.nastena.pawsitive.ui.screens.shelter.animal.add

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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.nastena.pawsitive.dto.AnimalBreed
import com.nastena.pawsitive.dto.AnimalGender
import com.nastena.pawsitive.dto.AnimalType
import com.nastena.pawsitive.ui.common.validation.ValidationState

@Composable
fun ShelterAddAnimalView(
    modifier: Modifier = Modifier,
    viewModel: ShelterAddAnimalViewModel
) {

    // ------------- States --------------------
    val nameState: ShelterAddAnimalState.Name by viewModel.nameState.collectAsState()
    val typeState: ShelterAddAnimalState.Type by viewModel.typeState.collectAsState()
    val breedState: ShelterAddAnimalState.Breed by viewModel.breedState.collectAsState()
    val genderState: ShelterAddAnimalState.Gender by viewModel.genderState.collectAsState()
    val birthDateState: ShelterAddAnimalState.BirthDate by viewModel.birthDateState.collectAsState()
    val descriptionState: ShelterAddAnimalState.Description by viewModel.descriptionState.collectAsState()

    ShelterAddAnimalView(
        modifier = modifier,
        nameState = nameState,
        typeState = typeState,
        breedState = breedState,
        genderState = genderState,
        birthDateState = birthDateState,
        descriptionState = descriptionState,
        onViewEvent = { event -> viewModel.onViewEvent(event) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BirthDatePicker(
    birthDateState: ShelterAddAnimalState.BirthDate,
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
                    java.text.SimpleDateFormat("dd.MM.yyyy").format(java.util.Date(it))
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
private fun ShelterAddAnimalView(
    modifier: Modifier = Modifier,
    nameState: ShelterAddAnimalState.Name,
    typeState: ShelterAddAnimalState.Type,
    breedState: ShelterAddAnimalState.Breed,
    genderState: ShelterAddAnimalState.Gender,
    birthDateState: ShelterAddAnimalState.BirthDate,
    descriptionState: ShelterAddAnimalState.Description,
    onViewEvent: (ShelterAddAnimalEvents) -> Unit
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
                text = stringResource(R.string.add_animal_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))


            // ------------- Name --------------------
            AnimatedVisibility(
                visible = nameState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (nameState.validation) {
                        ValidationState.Empty -> stringResource(R.string.add_animal_name_is_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.add_animal_name_invalid)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = nameState.text,
                onValueChange = { newText ->
                    onViewEvent(ShelterAddAnimalEvents.Name.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.add_animal_title)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = nameState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Birth date --------------------

            BirthDatePicker(
                birthDateState = birthDateState,
                onDateSelected = { selectedDate ->
                    onViewEvent(ShelterAddAnimalEvents.BirthDate.DateSelected(selectedDate))
                }
            )

            Spacer(modifier = Modifier.height(12.dp))


            // ------------- Choosing types --------------------
            AnimatedVisibility(
                visible = !typeState.isValid
            ) {
                OutlinedTextField(
                    value = if (typeState.isValid) {
                        ""
                    } else {
                        stringResource(R.string.add_animal_choose_type_invalid)
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onViewEvent(ShelterAddAnimalEvents.Type.ClickedType)
                }
            ) {
                val selectedTypeText: String = when (typeState.selected) {
                    AnimalType.CAT -> stringResource(R.string.add_animal_type_cat)
                    AnimalType.DOG -> stringResource(R.string.add_animal_type_dog)
                    null -> stringResource(R.string.add_animal_choose_type_label)
                }
                Text(
                    text = selectedTypeText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = typeState.isExpended,
                onDismissRequest = {
                    onViewEvent(ShelterAddAnimalEvents.Type.MenuDismissed)
                }
            ) {
                // ------------- CAT --------------------
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.add_animal_type_cat),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onViewEvent(
                            ShelterAddAnimalEvents.Type.TypeSelected(
                                AnimalType.CAT
                            )
                        )
                    }
                )

                // ------------- DOG --------------------
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.add_animal_type_dog),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onViewEvent(
                            ShelterAddAnimalEvents.Type.TypeSelected(
                                AnimalType.DOG
                            )
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            // ------------- Choosing genders --------------------
            AnimatedVisibility(
                visible = !genderState.isValid
            ) {
                OutlinedTextField(
                    value = if (genderState.isValid) {
                        ""
                    } else {
                        stringResource(R.string.add_animal_choose_gender_invalid)
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onViewEvent(ShelterAddAnimalEvents.Gender.ClickedGender)
                }
            ) {
                val selectedGenderText: String = when (genderState.selected) {
                    AnimalGender.FEMALE -> stringResource(R.string.add_animal_gender_female)
                    AnimalGender.MALE -> stringResource(R.string.add_animal_gender_male)
                    null -> stringResource(R.string.add_animal_choose_gender_label)
                }
                Text(
                    text = selectedGenderText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = genderState.isExpended,
                onDismissRequest = {
                    onViewEvent(ShelterAddAnimalEvents.Gender.MenuDismissed)
                }
            ) {
                // ------------- FEMALE --------------------
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.add_animal_gender_female),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onViewEvent(
                            ShelterAddAnimalEvents.Gender.GenderSelected(
                                AnimalGender.FEMALE
                            )
                        )
                    }
                )

                // ------------- MALE --------------------
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.add_animal_gender_male),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onViewEvent(
                            ShelterAddAnimalEvents.Gender.GenderSelected(
                                AnimalGender.MALE
                            )
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            // ------------- Choosing breeds --------------------
            AnimatedVisibility(
                visible = !breedState.isValid
            ) {
                OutlinedTextField(
                    value = if (breedState.isValid) {
                        ""
                    } else {
                        stringResource(R.string.add_animal_choose_gender_invalid)
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onViewEvent(ShelterAddAnimalEvents.Breed.ClickedBreed)
                }
            ) {
                val selectedBreedText: String = when (breedState.selected) {
                    AnimalBreed.LABRADOR_RETRIEVER -> stringResource(R.string.add_animal_breed_dog_LABRADOR_RETRIEVER)
                    AnimalBreed.DACHSHUND -> stringResource(R.string.add_animal_breed_dog_DACHSHUND)
                    AnimalBreed.METIS -> stringResource(R.string.add_animal_breed_cat_METIS)
                    AnimalBreed.SIAMESE -> stringResource(R.string.add_animal_breed_cat_SIAMESE)
                    null -> stringResource(R.string.add_animal_choose_breed_label)
                }
                Text(
                    text = selectedBreedText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            DropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = breedState.isExpended,
                onDismissRequest = {
                    onViewEvent(ShelterAddAnimalEvents.Breed.MenuDismissed)
                }
            ) {
                // ------------- LABRADOR_RETRIEVER --------------------
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.add_animal_breed_dog_LABRADOR_RETRIEVER),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onViewEvent(
                            ShelterAddAnimalEvents.Breed.BreedSelected(
                                AnimalBreed.LABRADOR_RETRIEVER
                            )
                        )
                    }
                )

                // ------------- DACHSHUND --------------------
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.add_animal_breed_dog_DACHSHUND),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onViewEvent(
                            ShelterAddAnimalEvents.Breed.BreedSelected(
                                AnimalBreed.DACHSHUND
                            )
                        )
                    }
                )

                // ------------- METIS --------------------
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.add_animal_breed_cat_METIS),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onViewEvent(
                            ShelterAddAnimalEvents.Breed.BreedSelected(
                                AnimalBreed.METIS
                            )
                        )
                    }
                )

                // ------------- SIAMESE --------------------
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.add_animal_breed_cat_SIAMESE),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onViewEvent(
                            ShelterAddAnimalEvents.Breed.BreedSelected(
                                AnimalBreed.SIAMESE
                            )
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ------------- Description --------------------
            AnimatedVisibility(
                visible = descriptionState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (descriptionState.validation) {
                        ValidationState.Empty -> stringResource(R.string.add_animal_description_is_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.add_animal_description_invalid)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = descriptionState.text,
                onValueChange = { newText ->
                    onViewEvent(ShelterAddAnimalEvents.Description.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.add_animal_description_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = nameState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))


            // ------------- Buttons --------------------
            Button(
                onClick = { onViewEvent(ShelterAddAnimalEvents.AddClicked) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.add_animal_clicked))
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = { onViewEvent(ShelterAddAnimalEvents.CancelClicked) },
            ) {
                Text(stringResource(R.string.add_animal_cancel_clicked))
            }

        }

    }

}



