package com.nastena.pawsitive.ui.screens.shelter.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

    val email by viewModel.emailState.collectAsState()
    val name by viewModel.nameState.collectAsState()
    val address by viewModel.addressState.collectAsState()
    val phone by viewModel.phoneState.collectAsState()
    val info by viewModel.infoState.collectAsState()

    ShelterProfileView(
        modifier = modifier,
        email = email,
        name = name,
        address = address,
        phone = phone,
        info = info,
        onViewEvent = { viewModel.onViewEvent(it) }
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

            Text(
                text = stringResource(R.string.shelter_profile_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(32.dp))

            ProfileField(
                value = name,
                label = R.string.shelter_profile_name,
                icon = Icons.Default.Person
            )

            Spacer(Modifier.height(16.dp))

            ProfileField(
                value = email,
                label = R.string.shelter_profile_email,
                icon = Icons.Default.Email
            )

            Spacer(Modifier.height(16.dp))

            ProfileField(
                value = phone,
                label = R.string.shelter_profile_phone,
                icon = Icons.Default.Phone
            )

            Spacer(Modifier.height(16.dp))

            ProfileField(
                value = address,
                label = R.string.shelter_profile_address,
                icon = Icons.Default.LocationOn
            )

            Spacer(Modifier.height(16.dp))

            ProfileField(
                value = info,
                label = R.string.shelter_profile_info,
                icon = Icons.Default.Info
            )

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { onViewEvent(ShelterProfileEvents.EditingClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(stringResource(R.string.shelter_profile_editing))
            }

            Spacer(Modifier.height(12.dp))

            TextButton(
                onClick = { onViewEvent(ShelterProfileEvents.LogoutClicked) }
            ) {
                Text(stringResource(R.string.shelter_profile_logout_submit))
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfileField(
    value: String,
    label: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        readOnly = true,
        label = { Text(stringResource(label)) },
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(icon, contentDescription = null)
        },
        singleLine = false,
        shape = MaterialTheme.shapes.medium
    )
}


//package com.nastena.pawsitive.ui.screens.shelter.profile
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import com.nastena.pawsitive.R
//
//@Composable
//fun ShelterProfileView(
//    modifier: Modifier = Modifier,
//    viewModel: ShelterProfileViewModel
//) {
//
//    val email: String by viewModel.emailState.collectAsState()
//    val name: String by viewModel.nameState.collectAsState()
//    val address: String by viewModel.addressState.collectAsState()
//    val phone: String by viewModel.phoneState.collectAsState()
//    val info: String by viewModel.infoState.collectAsState()
//
//
//    ShelterProfileView(
//        modifier = modifier,
//        email = email,
//        name = name,
//        address = address,
//        phone = phone,
//        info = info,
//        onViewEvent = { event -> viewModel.onViewEvent(event) }
//    )
//}
//
//@Composable
//private fun ShelterProfileView(
//    modifier: Modifier = Modifier,
//    email: String,
//    name: String,
//    address: String,
//    phone: String,
//    info: String,
//    onViewEvent: (ShelterProfileEvents) -> Unit
//) {
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            // ------------- Title --------------------
//            Text(
//                text = stringResource(R.string.shelter_profile_title),
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//
//            // ------------- Name --------------------
//
//            TextField(name, R.string.shelter_profile_name)
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // ------------- Email --------------------
//
//            TextField(email, R.string.shelter_profile_email)
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // ------------- Phone --------------------
//
//            TextField(phone, R.string.shelter_profile_phone)
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // ------------- Address --------------------
//
//            TextField(address, R.string.shelter_profile_address)
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // ------------- Info --------------------
//
//            TextField(info, R.string.shelter_profile_info)
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//
//            // ------------- Buttons --------------------
//            Button(
//                onClick = { onViewEvent(ShelterProfileEvents.EditingClicked) },
//                modifier = Modifier.fillMaxWidth(),
//            ) {
//                Text(stringResource(R.string.shelter_profile_editing))
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Button(
//                onClick = { onViewEvent(ShelterProfileEvents.LogoutClicked) },
//                modifier = Modifier.fillMaxWidth(),
//            ) {
//                Text(stringResource(R.string.shelter_profile_logout_submit))
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//        }
//    }
//}
//
//@Composable
//private fun TextField(
//    value: String, labelId: Int
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = {},
//        label = { Text(stringResource(labelId)) },
//        readOnly = true,
//        modifier = Modifier.fillMaxWidth(),
//    )
//}