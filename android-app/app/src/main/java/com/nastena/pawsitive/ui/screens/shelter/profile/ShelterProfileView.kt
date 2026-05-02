package com.nastena.pawsitive.ui.screens.shelter.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nastena.pawsitive.R
import com.nastena.pawsitive.ui.common.PawsitiveTextButton

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

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {

            item {

                Text(
                    text = stringResource(R.string.shelter_profile_title),
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(24.dp))


                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        ProfileRow(Icons.Default.Person, name)
                        Spacer(Modifier.height(12.dp))

                        ProfileRow(Icons.Default.Email, email)
                        Spacer(Modifier.height(12.dp))

                        ProfileRow(Icons.Default.Phone, phone)
                        Spacer(Modifier.height(12.dp))

                        ProfileRow(Icons.Default.LocationOn, address)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = stringResource(R.string.shelter_profile_info),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = info,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onViewEvent(ShelterProfileEvents.EditingClicked) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        stringResource(R.string.shelter_profile_editing)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))


                PawsitiveTextButton(
                    onClick = { onViewEvent(ShelterProfileEvents.LogoutClicked) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(stringResource(R.string.shelter_profile_logout_submit))
                }


                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun ProfileRow(
    icon: ImageVector,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}