package com.nastena.pawsitive.ui.screens.shelter.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import com.nastena.pawsitive.R

@Composable
fun ShelterProfileView(
    modifier: Modifier = Modifier,
    viewModel: ShelterProfileViewModel
) {

    val email: String by viewModel.emailState.collectAsState()
    val name: String by viewModel.nameState.collectAsState()
    val address: String by viewModel.addressState.collectAsState()
    val phone: String by viewModel.phoneState.collectAsState()
    val info: String by viewModel.infoState.collectAsState()


    ShelterProfileView(
        modifier = modifier,
        email = email,
        name = name,
        address = address,
        phone = phone,
        info = info,
        onViewEvent = { event -> viewModel.onViewEvent(event) }
    )
}

@Composable
private fun ShelterProfileView(
    modifier: Modifier = Modifier,
    email: String,
    name: String,
    address: String,
    phone: String,
    info: String,
    onViewEvent: (ShelterProfileEvents) -> Unit
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
                text = stringResource(R.string.shelter_profile_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))


            // ------------- Name --------------------

            TextField(name, R.string.shelter_profile_name)

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Email --------------------

            TextField(email, R.string.shelter_profile_email)

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Phone --------------------

            TextField(phone, R.string.shelter_profile_phone)

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Address --------------------

            TextField(address, R.string.shelter_profile_address)

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Info --------------------

            TextField(info, R.string.shelter_profile_info)

            Spacer(modifier = Modifier.height(12.dp))


            // ------------- Buttons --------------------
            Button(
                onClick = { onViewEvent(ShelterProfileEvents.EditingClicked) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.shelter_profile_editing))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { onViewEvent(ShelterProfileEvents.LogoutClicked) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.shelter_profile_logout_submit))
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun TextField(
    value: String, labelId: Int
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(stringResource(labelId)) },
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
    )
}