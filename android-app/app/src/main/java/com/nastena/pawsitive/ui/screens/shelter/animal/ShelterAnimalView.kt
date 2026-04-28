package com.nastena.pawsitive.ui.screens.shelter.animal

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nastena.pawsitive.R
import com.nastena.pawsitive.dto.AnimalBreed
import com.nastena.pawsitive.dto.AnimalGender
import com.nastena.pawsitive.dto.AnimalType
import com.nastena.pawsitive.ui.common.validation.ValidationState
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ShelterAnimalView(
    modifier: Modifier = Modifier,
    viewModel: ShelterAnimalViewModel
) {

    // ------------- States --------------------
    val modeState: ShelterAnimalState.Mode by viewModel.mode.collectAsState()
    val nameState: ShelterAnimalState.Name by viewModel.nameState.collectAsState()
    val typeState: ShelterAnimalState.Type by viewModel.typeState.collectAsState()
    val breedState: ShelterAnimalState.Breed by viewModel.breedState.collectAsState()
    val genderState: ShelterAnimalState.Gender by viewModel.genderState.collectAsState()
    val birthDateState: ShelterAnimalState.BirthDate by viewModel.birthDateState.collectAsState()
    val descriptionState: ShelterAnimalState.Description by viewModel.descriptionState.collectAsState()
    val animalPhotosState: ShelterAnimalState.Photos by viewModel.animalPhotosState.collectAsState()

    ShelterAnimalView(
        modifier = modifier,
        modeState = modeState,
        nameState = nameState,
        typeState = typeState,
        breedState = breedState,
        genderState = genderState,
        birthDateState = birthDateState,
        descriptionState = descriptionState,
        animalPhotosState = animalPhotosState,
        onViewEvent = { event -> viewModel.onViewEvent(event) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BirthDatePicker(
    birthDateState: ShelterAnimalState.BirthDate,
    onDateSelected: (Long) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = birthDateState.date
    )

    Column {
        Text(
            text = stringResource(R.string.add_animal_birth_date_label),
            style = MaterialTheme.typography.labelMedium,
            color = if (!birthDateState.isValid)
                MaterialTheme.colorScheme.error
            else
                MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )

        OutlinedButton(
            onClick = { openDialog.value = true },
            modifier = Modifier.fillMaxWidth(),
            border = if (!birthDateState.isValid)
                androidx.compose.foundation.BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.error
                )
            else null
        ) {

            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )

            Spacer(Modifier.size(8.dp))

            Text(
                text = birthDateState.date?.let {
                    SimpleDateFormat("dd.MM.yyyy").format(Date(it))
                } ?: stringResource(R.string.add_animal_birth_date_choose)
            )
        }

        AnimatedVisibility(visible = !birthDateState.isValid) {
            Text(
                text = stringResource(R.string.add_animal_birth_date_error),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
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
private fun ShelterAnimalView(
    modifier: Modifier = Modifier,
    modeState: ShelterAnimalState.Mode,
    nameState: ShelterAnimalState.Name,
    typeState: ShelterAnimalState.Type,
    breedState: ShelterAnimalState.Breed,
    genderState: ShelterAnimalState.Gender,
    birthDateState: ShelterAnimalState.BirthDate,
    descriptionState: ShelterAnimalState.Description,
    animalPhotosState: ShelterAnimalState.Photos,
    onViewEvent: (ShelterAnimalEvents) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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

            Spacer(Modifier.height(32.dp))


            // ------------- Title --------------------
            Text(
                text = when (modeState) {
                    ShelterAnimalState.Mode.Add -> stringResource(R.string.add_animal_title)
                    is ShelterAnimalState.Mode.Edit -> stringResource(R.string.edit_animal_title)
                },
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))


            // ------------- Name --------------------

            OutlinedTextField(
                value = nameState.text,
                onValueChange = {
                    onViewEvent(ShelterAnimalEvents.Name.TextUpdated(it))
                },
                label = { Text(stringResource(R.string.add_animal_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Pets, contentDescription = null)
                },
                singleLine = true,
                isError = nameState.validation != ValidationState.Valid,
                supportingText = {
                    when (nameState.validation) {
                        ValidationState.Empty ->
                            Text(stringResource(R.string.not_empty))

                        ValidationState.InvalidFormat ->
                            Text(stringResource(R.string.add_animal_name_invalid))

                        else -> {}
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            // ------------- Birth date --------------------

            BirthDatePicker(
                birthDateState = birthDateState,
                onDateSelected = { selectedDate ->
                    onViewEvent(ShelterAnimalEvents.BirthDate.DateSelected(selectedDate))
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
                    onViewEvent(ShelterAnimalEvents.Type.ClickedType)
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
                    onViewEvent(ShelterAnimalEvents.Type.MenuDismissed)
                }
            ) {
                AnimalType.entries.forEach { animalType: AnimalType ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = when (animalType) {
                                    AnimalType.DOG -> stringResource(R.string.add_animal_type_dog)
                                    AnimalType.CAT -> stringResource(R.string.add_animal_type_cat)
                                },
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        onClick = {
                            onViewEvent(
                                ShelterAnimalEvents.Type.TypeSelected(
                                    animalType
                                )
                            )
                        }
                    )
                }
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
                    onViewEvent(ShelterAnimalEvents.Gender.ClickedGender)
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
                    onViewEvent(ShelterAnimalEvents.Gender.MenuDismissed)
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
                            ShelterAnimalEvents.Gender.GenderSelected(
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
                            ShelterAnimalEvents.Gender.GenderSelected(
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
                        stringResource(R.string.add_animal_choose_breed_invalid)
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onViewEvent(ShelterAnimalEvents.Breed.ClickedBreed)
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
                    onViewEvent(ShelterAnimalEvents.Breed.MenuDismissed)
                }
            ) {
                breedState.options.forEach { breed ->

                    DropdownMenuItem(
                        text = {
                            Text(
                                text = when (breed) {
                                    AnimalBreed.LABRADOR_RETRIEVER ->
                                        stringResource(R.string.add_animal_breed_dog_LABRADOR_RETRIEVER)

                                    AnimalBreed.DACHSHUND ->
                                        stringResource(R.string.add_animal_breed_dog_DACHSHUND)

                                    AnimalBreed.METIS ->
                                        stringResource(R.string.add_animal_breed_cat_METIS)

                                    AnimalBreed.SIAMESE ->
                                        stringResource(R.string.add_animal_breed_cat_SIAMESE)
                                }
                            )
                        },
                        onClick = {
                            onViewEvent(
                                ShelterAnimalEvents.Breed.BreedSelected(breed)
                            )
                        }
                    )

                }

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
                    onViewEvent(ShelterAnimalEvents.Description.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.add_animal_description_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = descriptionState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- PHOTOS --------------------
            AnimalPhotosSection(
                title = stringResource(R.string.add_animal_photo_title),
                photos = animalPhotosState.animal,
                maxPhotos = 3,
                onAddPhoto = { uri ->
                    onViewEvent(ShelterAnimalEvents.Photos.AddAnimalPhotos(uri))
                },
                onRemovePhoto = { uri ->
                    onViewEvent(ShelterAnimalEvents.Photos.RemoveAnimalPhotos(uri))
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            AnimalPhotosSection(
                title = stringResource(R.string.add_animal_passport_title),
                photos = animalPhotosState.passport,
                maxPhotos = 15,
                onAddPhoto = { uri ->
                    onViewEvent(ShelterAnimalEvents.Photos.AddPassportAnimalPhotos(uri))
                },
                onRemovePhoto = { uri ->
                    onViewEvent(ShelterAnimalEvents.Photos.RemovePassportAnimalPhotos(uri))
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Buttons --------------------
            Button(
                onClick = { onViewEvent(ShelterAnimalEvents.SaveChangeClicked) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    stringResource(R.string.save_animal_clicked)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = { onViewEvent(ShelterAnimalEvents.CancelClicked) },
            ) {
                Text(stringResource(R.string.add_animal_cancel_clicked))
            }
        }

    }

}

@Composable
private fun AnimalPhotosSection(
    title: String,
    photos: List<String>,
    maxPhotos: Int,
    onAddPhoto: (String) -> Unit,
    onRemovePhoto: (String) -> Unit
) {
    var photoToDelete by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(title, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                onAddPhoto(it.toString())
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            photos.forEach { uri ->
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 8.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(uri)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .matchParentSize()
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                shape = MaterialTheme.shapes.small
                            ),
                        error = painterResource(R.drawable.ic_image_error)
                    )

                    Text(
                        text = "✕",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .clickable {
                                photoToDelete = uri
                            },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            if (photos.size < maxPhotos) {
                OutlinedButton(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 8.dp)
                ) {
                    Text("+", style = MaterialTheme.typography.headlineSmall)
                }
            }

            if (photoToDelete != null) {
                AlertDialog(
                    onDismissRequest = { photoToDelete = null },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                onRemovePhoto(photoToDelete!!)
                                photoToDelete = null
                            }
                        ) {
                            Text(stringResource(R.string.remove_animal_photo))
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { photoToDelete = null }
                        ) {
                            Text(stringResource(R.string.cancel_remove_animal_photo))
                        }
                    },
                    title = { Text(stringResource(R.string.question_remove_animal_photo)) },
                    text = { Text(stringResource(R.string.warning_remove_animal_photo)) }
                )
            }
        }
    }
}





