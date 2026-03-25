package com.nastena.pawsitive.ui.screens.shelter.editing

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
fun EditingShelterProfileView(
    modifier: Modifier = Modifier,
    viewModel: EditingShelterProfileViewModel
) {

    val phone: EditingShelterProfileState.Phone by viewModel.phoneState.collectAsState()
    val address: String by viewModel.addressState.collectAsState()
    val info: String by viewModel.infoState.collectAsState()


    EditingShelterProfileView(
        modifier = modifier,
        address = address,
        phone = phone,
        info = info,
        onViewEvent = { event -> viewModel.onViewEvent(event) }
    )
}

@Composable
private fun EditingShelterProfileView(
    modifier: Modifier = Modifier,
    address: String,
    phone: EditingShelterProfileState.Phone,
    info: String,
    onViewEvent: (EditingShelterProfileEvents) -> Unit
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
                text = stringResource(R.string.editing_shelter_profile_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ------------- Phone --------------------
            AnimatedVisibility(
                visible = phone.validation != ValidationState.Valid
            ) {
                OutlinedTextField(
                    value = when (phone.validation) {
                        ValidationState.Empty -> stringResource(R.string.editing_shelter_phone_empty)
                        ValidationState.InvalidFormat -> stringResource(R.string.editing_shelter_phone_invalid)
                        ValidationState.Valid -> ""
                    },
                    onValueChange = {},
                    readOnly = true,
                    isError = true

                )
            }

            OutlinedTextField(
                value = phone.text,
                onValueChange = { newText ->
                    val digits = newText.filter { it.isDigit() }.take(10)
                    onViewEvent(EditingShelterProfileEvents.Phone.TextUpdated(digits))
                },
                label = { Text(stringResource(R.string.editing_shelter_profile_phone)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = phone.validation != ValidationState.Valid,
                visualTransformation = PhoneVisualTransformation("+7-000-000-00-00", '0')
            )
            Spacer(modifier = Modifier.height(12.dp))


            // ------------- Address --------------------
            OutlinedTextField(
                value = address,
                onValueChange = { newText ->
                    onViewEvent(EditingShelterProfileEvents.Address.TextUpdated(newText))
                },
                label = { Text(stringResource(R.string.editing_shelter_profile_address)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Info --------------------
            OutlinedTextField(
                value = info,
                onValueChange = { newText ->
                    onViewEvent(EditingShelterProfileEvents.Info.TextUpdated(newText))
                },
                label = { Text(stringResource(R.string.editing_shelter_profile_info)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Buttons --------------------
            Button(
                onClick = { onViewEvent(EditingShelterProfileEvents.CancelClicked) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.editing_shelter_profile_cancel))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { onViewEvent(EditingShelterProfileEvents.SaveChangedClicked) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.editing_shelter_profile_save))
            }

        }
    }
}