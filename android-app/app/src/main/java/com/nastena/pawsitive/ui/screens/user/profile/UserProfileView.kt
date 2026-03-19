package com.nastena.pawsitive.ui.screens.user.profile

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
fun UserProfileView(
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel
) {

    val state: UserProfileState by viewModel.state.collectAsState()

    UserProfileView(
        modifier = modifier,
        state = state,
        onViewEvent = { event -> viewModel.onViewEvent(event) }
    )
}

@Composable
private fun UserProfileView(
    modifier: Modifier = Modifier,
    state: UserProfileState,
    onViewEvent: (UserProfileViewEvents) -> Unit
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
                text = stringResource(R.string.user_profile_title),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))


            // ------------- Name --------------------

            OutlinedTextField(
                value = state.name,
                onValueChange = {},
                label = { Text(stringResource(R.string.user_profile_name)) },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(12.dp))


            // ------------- Email --------------------

            OutlinedTextField(
                value = state.email,
                onValueChange = {},
                label = { Text(stringResource(R.string.user_profile_email)) },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ------------- Buttons --------------------
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



