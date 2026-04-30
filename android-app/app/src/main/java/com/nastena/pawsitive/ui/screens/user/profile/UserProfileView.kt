package com.nastena.pawsitive.ui.screens.user.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.nastena.pawsitive.R
import com.nastena.pawsitive.dto.AdoptionStatus

@Composable
fun UserProfileView(
    modifier: Modifier = Modifier,
    viewModel: UserProfileViewModel
) {

    val email by viewModel.emailState.collectAsState()
    val name by viewModel.nameState.collectAsState()
    val requests by viewModel.adoptionState.collectAsState()

    val confirmFormCancel by viewModel.confirmFormCancel.collectAsState()

    UserProfileView(
        modifier = modifier,
        email = email,
        name = name,
        requests = requests,
        confirmFormCancel = confirmFormCancel,
        onConfirmCancel = { viewModel.onConfirmCancel(it) },
        onViewEvent = { event -> viewModel.onViewEvent(event) }
    )
}

@Composable
private fun UserProfileView(
    modifier: Modifier = Modifier,
    email: String,
    name: String,
    requests: List<UserProfileState.Requests>,
    confirmFormCancel: UserProfileState.ConfirmFormCancel?,
    onConfirmCancel: (Boolean) -> Unit,
    onViewEvent: (UserProfileEvents) -> Unit
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

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Person, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = name)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Email, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = email)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Text(
                    text = stringResource(R.string.user_forms_title),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            if (requests.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.no_requests),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                items(requests.size) { index ->

                    val requestState = requests[index]

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        if (requestState.photoUrls.isNotEmpty()) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(requestState.photoUrls[0])
                                    .transformations(CircleCropTransformation())
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier.size(80.dp),
                                error = painterResource(R.drawable.ic_image_error)
                            )
                        }

                        Column {

                            Text(
                                text = requestState.animalName,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = requestState.shelterName,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Text(
                                text = when (requestState.status) {
                                    AdoptionStatus.PENDING -> stringResource(R.string.adoption_status_pending)
                                    AdoptionStatus.APPROVED -> stringResource(R.string.adoption_status_approved)
                                    AdoptionStatus.REJECTED -> stringResource(R.string.adoption_status_rejected)
                                    else -> ""
                                }

                            )
                        }

                        IconButton(
                            onClick = {
                                onViewEvent(UserProfileEvents.CancelRequestClicked(index))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Cancel,
                                contentDescription = null
                            )
                        }
                    }
                }
            }

            item {

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onViewEvent(UserProfileEvents.LogoutClicked) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(stringResource(R.string.user_profile_logout_submit))
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }

    if (confirmFormCancel?.isVisible == true) {
        AlertDialog(
            onDismissRequest = { onConfirmCancel(false) },
            title = { Text(stringResource(R.string.cancel_request_button)) },
            text = { Text(stringResource(R.string.warning_cancel_request)) },
            confirmButton = {
                TextButton(onClick = { onConfirmCancel(false) }) {
                    Text(stringResource(R.string.cancel_cancel_request_no))
                }
            },
            dismissButton = {
                TextButton(onClick = { onConfirmCancel(true) }) {
                    Text(stringResource(R.string.cancel_request_button_yes))
                }
            }
        )
    }
}