package com.nastena.pawsitive.ui.screens.user.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nastena.pawsitive.R

@Composable
fun UserProfileView(
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel
) {

    val email by viewModel.emailState.collectAsState()
    val name by viewModel.nameState.collectAsState()
    val requests by viewModel.adoptionState.collectAsState()

    UserProfileView(
        modifier = modifier,
        email = email,
        name = name,
        requests = requests,
        onViewEvent = { event -> viewModel.onViewEvent(event) }
    )
}

@Composable
private fun UserProfileView(
    modifier: Modifier = Modifier,
    email: String,
    name: String,
    requests: List<UserProfileState.Requests>,
    onViewEvent: (UserProfileViewEvents) -> Unit
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
                    text = stringResource(R.string.user_profile_title),
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = {},
                    label = { Text(stringResource(R.string.user_profile_name)) },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {},
                    label = { Text(stringResource(R.string.user_profile_email)) },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.user_forms_title),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            items(requests) { request ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {

                    Text(
                        text = request.animalName,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = request.shelterName,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = request.status.name,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            item {

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onViewEvent(UserProfileViewEvents.LogoutClicked) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(stringResource(R.string.user_profile_logout_submit))
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}