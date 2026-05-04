package com.nastena.pawsitive.ui.screens.shelter.profile.editing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
    val phone by viewModel.phoneState.collectAsState()
    val address by viewModel.addressState.collectAsState()
    val info by viewModel.infoState.collectAsState()

    EditingShelterProfileView(
        modifier = modifier,
        phone = phone,
        address = address,
        info = info,
        onEvent = { viewModel.onViewEvent(it) }
    )
}

@Composable
private fun EditingShelterProfileView(
    modifier: Modifier = Modifier,
    phone: EditingShelterProfileState.Phone,
    address: String,
    info: String,
    onEvent: (EditingShelterProfileEvents) -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier
                .padding(24.dp)
                .widthIn(max = 420.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(32.dp))

            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(72.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.editing_shelter_profile_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(32.dp))

            // PHONE
            OutlinedTextField(
                value = phone.text,
                onValueChange = {
                    val digits = it.filter { c -> c.isDigit() }.take(10)
                    onEvent(EditingShelterProfileEvents.Phone.TextUpdated(digits))
                },
                label = { Text(stringResource(R.string.editing_shelter_profile_phone)) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Phone, contentDescription = null)
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                isError = phone.validation != ValidationState.Valid,
                supportingText = {
                    when (phone.validation) {
                        ValidationState.Empty ->
                            Text(stringResource(R.string.editing_shelter_phone_empty))

                        ValidationState.InvalidFormat ->
                            Text(stringResource(R.string.editing_shelter_phone_invalid))

                        else -> {}
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            // ADDRESS
            OutlinedTextField(
                value = address,
                onValueChange = {
                    onEvent(EditingShelterProfileEvents.Address.TextUpdated(it))
                },
                label = { Text(stringResource(R.string.editing_shelter_profile_address)) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.LocationOn, contentDescription = null)
                },
                singleLine = false,
            )

            Spacer(Modifier.height(16.dp))

            // INFO
            OutlinedTextField(
                value = info,
                onValueChange = {
                    onEvent(EditingShelterProfileEvents.Info.TextUpdated(it))
                },
                label = { Text(stringResource(R.string.editing_shelter_profile_info)) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Info, contentDescription = null)
                },
                singleLine = false,
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { onEvent(EditingShelterProfileEvents.SaveChangedClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(stringResource(R.string.editing_shelter_profile_save))
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { onEvent(EditingShelterProfileEvents.CancelClicked) }
            ) {
                Text(stringResource(R.string.editing_shelter_profile_cancel))
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}
