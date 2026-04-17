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
    val animalState: FormState.Animal by viewModel.animalState.collectAsState()
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
    animalState: FormState.Animal,
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

            Text(text = animalState.name)

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
        }
    }
}