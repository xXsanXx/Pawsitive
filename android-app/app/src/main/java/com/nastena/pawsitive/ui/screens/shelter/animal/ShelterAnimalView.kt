package com.nastena.pawsitive.ui.screens.shelter.animal

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nastena.pawsitive.R
import com.nastena.pawsitive.dto.AnimalBreed
import com.nastena.pawsitive.dto.AnimalGender
import com.nastena.pawsitive.dto.AnimalType
import com.nastena.pawsitive.ui.common.AnimalImage
import com.nastena.pawsitive.ui.common.PawsitiveTextButton
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
                    Icon(Icons.Default.Badge, contentDescription = null)
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

            Spacer(modifier = Modifier.height(24.dp))

            // ------------- Birth date --------------------

            BirthDatePicker(
                birthDateState = birthDateState,
                onDateSelected = { selectedDate ->
                    onViewEvent(ShelterAnimalEvents.BirthDate.DateSelected(selectedDate))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))


            // ------------- Choosing types --------------------

            Column {

                @OptIn(ExperimentalMaterial3Api::class)
                ExposedDropdownMenuBox(
                    expanded = typeState.isExpended,
                    onExpandedChange = {
                        onViewEvent(ShelterAnimalEvents.Type.ClickedType)
                    }
                ) {

                    OutlinedTextField(
                        value = when (typeState.selected) {
                            AnimalType.CAT -> stringResource(R.string.add_animal_type_cat)
                            AnimalType.DOG -> stringResource(R.string.add_animal_type_dog)
                            null -> stringResource(R.string.add_animal_choose_type_label)
                        },
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        label = { Text(stringResource(R.string.add_animal_type_label)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = typeState.isExpended
                            )
                        },
                        isError = !typeState.isValid
                    )

                    ExposedDropdownMenu(
                        expanded = typeState.isExpended,
                        onDismissRequest = {
                            onViewEvent(ShelterAnimalEvents.Type.MenuDismissed)
                        }
                    ) {
                        AnimalType.entries.forEach { animalType ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        when (animalType) {
                                            AnimalType.DOG -> stringResource(R.string.add_animal_type_dog)
                                            AnimalType.CAT -> stringResource(R.string.add_animal_type_cat)
                                        }
                                    )
                                },
                                onClick = {
                                    onViewEvent(
                                        ShelterAnimalEvents.Type.TypeSelected(animalType)
                                    )
                                }
                            )
                        }
                    }
                }

                AnimatedVisibility(visible = !typeState.isValid) {
                    Text(
                        text = stringResource(R.string.required_field),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))


            // ------------- Choosing genders --------------------

            Column {

                @OptIn(ExperimentalMaterial3Api::class)
                ExposedDropdownMenuBox(
                    expanded = genderState.isExpended,
                    onExpandedChange = {
                        onViewEvent(ShelterAnimalEvents.Gender.ClickedGender)
                    }
                ) {

                    OutlinedTextField(
                        value = when (genderState.selected) {
                            AnimalGender.FEMALE -> stringResource(R.string.add_animal_gender_female)
                            AnimalGender.MALE -> stringResource(R.string.add_animal_gender_male)
                            null -> stringResource(R.string.add_animal_choose_gender_label)
                        },
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        label = { Text(stringResource(R.string.add_animal_gender_label)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = genderState.isExpended
                            )
                        },
                        isError = !genderState.isValid
                    )

                    ExposedDropdownMenu(
                        expanded = genderState.isExpended,
                        onDismissRequest = {
                            onViewEvent(ShelterAnimalEvents.Gender.MenuDismissed)
                        }
                    ) {

                        DropdownMenuItem(
                            text = {
                                Text(stringResource(R.string.add_animal_gender_female))
                            },
                            onClick = {
                                onViewEvent(
                                    ShelterAnimalEvents.Gender.GenderSelected(AnimalGender.FEMALE)
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text(stringResource(R.string.add_animal_gender_male))
                            },
                            onClick = {
                                onViewEvent(
                                    ShelterAnimalEvents.Gender.GenderSelected(AnimalGender.MALE)
                                )
                            }
                        )
                    }
                }

                AnimatedVisibility(visible = !genderState.isValid) {
                    Text(
                        text = stringResource(R.string.required_field),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // ------------- Choosing breeds --------------------

            Column {

                @OptIn(ExperimentalMaterial3Api::class)
                ExposedDropdownMenuBox(
                    expanded = breedState.isExpended,
                    onExpandedChange = {
                        onViewEvent(ShelterAnimalEvents.Breed.ClickedBreed)
                    }
                ) {

                    OutlinedTextField(
                        value = when (breedState.selected) {
                            AnimalBreed.LABRADOR_RETRIEVER -> stringResource(R.string.add_animal_breed_dog_LABRADOR_RETRIEVER)
                            AnimalBreed.DACHSHUND -> stringResource(R.string.add_animal_breed_dog_DACHSHUND)
                            AnimalBreed.METIS -> stringResource(R.string.add_animal_breed_cat_METIS)
                            AnimalBreed.SIAMESE -> stringResource(R.string.add_animal_breed_cat_SIAMESE)
                            null -> stringResource(R.string.add_animal_choose_breed_label)
                        },
                        onValueChange = {},
                        readOnly = true,
                        enabled = typeState.selected != null,
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        label = { Text(stringResource(R.string.add_animal_breed_label)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = breedState.isExpended
                            )
                        },
                        isError = !breedState.isValid
                    )

                    ExposedDropdownMenu(
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
                }

                AnimatedVisibility(visible = !breedState.isValid) {
                    Text(
                        text = stringResource(R.string.required_field),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))


            // ------------- Description --------------------

            OutlinedTextField(
                value = descriptionState.text,
                onValueChange = {
                    onViewEvent(ShelterAnimalEvents.Description.TextUpdated(it))
                },
                label = { Text(stringResource(R.string.add_animal_description_label)) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Description, contentDescription = null)
                },
                singleLine = false,
                isError = descriptionState.validation != ValidationState.Valid,
                supportingText = {
                    when (descriptionState.validation) {
                        ValidationState.Empty ->
                            Text(stringResource(R.string.not_empty))

                        ValidationState.InvalidFormat ->
                            Text(stringResource(R.string.add_animal_description_invalid))

                        else -> {}
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))


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

            Spacer(modifier = Modifier.height(24.dp))

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

            PawsitiveTextButton(
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

    val context = LocalContext.current

    Card {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(12.dp))

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri ->
                uri?.let { onAddPhoto(it.toString()) }
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(photos) { uri ->

                    Box(
                        modifier = Modifier.size(70.dp)
                    ) {

                        AnimalImage(
                            Modifier
                                .fillMaxSize()
                                .clip(
                                    MaterialTheme.shapes.medium,
                                ),
                            uri
                        )

                        IconButton(
                            onClick = { onRemovePhoto(uri) },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(Icons.Default.Close, null)
                        }
                    }
                }

                if (photos.size < maxPhotos) {
                    item {
                        OutlinedButton(
                            onClick = { launcher.launch("image/*") },
                            modifier = Modifier.size(70.dp)
                        ) {
                            Icon(Icons.Default.Add, null)
                        }
                    }
                }
            }
        }
    }
}

