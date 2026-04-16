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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nastena.pawsitive.R
import com.nastena.pawsitive.ui.common.validation.ValidationState

@Composable
fun FormView(
    modifier: Modifier = Modifier,
    viewModel: FormViewModel
) {
    // ------------- States --------------------
    val animalState: FormState.AnimalName by viewModel.animalName.collectAsState()
    val fullNameState: FormState.FullName by viewModel.fullNameState.collectAsState()
    val ageState: FormState.Age by viewModel.ageState.collectAsState()
    val professionState: FormState.Profession by viewModel.professionState.collectAsState()
    val phoneState: FormState.Phone by viewModel.phoneState.collectAsState()

    FormView(
        modifier = modifier,
        animalState = animalState,
        fullNameState = fullNameState,
        ageState = ageState,
        professionState = professionState,
        phoneState = phoneState,
        onViewEvent = { event -> viewModel.onViewEvent(event) }
    )
}

@Composable
private fun FormView(
    modifier: Modifier = Modifier,
    animalState: FormState.AnimalName,
    fullNameState: FormState.FullName,
    ageState: FormState.Age,
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
            AnimatedVisibility(
                visible = animalState.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (animalState.validation) {
                        ValidationState.Empty -> stringResource(R.string.animal_name_is_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.animal_name_invalid)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = animalState.text,
                onValueChange = { newText ->
                    onViewEvent(FormEvents.AnimalName.TextUpdated(newText))

                },
                label = { Text(stringResource(R.string.animal_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = animalState.validation != ValidationState.Valid
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}