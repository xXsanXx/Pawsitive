package com.nastena.pawsitive.ui.screens.shelter.requests

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nastena.pawsitive.R
import com.nastena.pawsitive.dto.AdoptionStatus
import com.nastena.pawsitive.ui.common.AnimalImage
import com.nastena.pawsitive.ui.common.isFinal

@Composable
fun ShelterRequestsView(
    modifier: Modifier = Modifier,
    viewModel: ShelterRequestsViewModel
) {
    val form by viewModel.formState.collectAsState()
    val confirmForm by viewModel.confirmFormState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {

        item {
            Text(
                text = stringResource(R.string.shelter_forms_title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }

        items(form.size) { index ->
            ShelterRequestCard(
                form = form[index],
                onClick = {
                    viewModel.onViewEvent(
                        ShelterRequestsEvents.GoToFormClicked(index)
                    )
                },
                onHide = {
                    viewModel.onViewEvent(
                        ShelterRequestsEvents.HideRequest(index)
                    )
                }
            )
        }
    }

    if (confirmForm != null) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onViewEvent(ShelterRequestsEvents.ConfirmCancelClicked(false))
            },
            title = {
                Text(
                    stringResource(R.string.hide_request_button)
                )
            },
            text = { Text(stringResource(R.string.warning_cancel_request)) },
            confirmButton = {
                Button(onClick = {
                    viewModel.onViewEvent(
                        ShelterRequestsEvents.ConfirmCancelClicked(true)
                    )
                }) {
                    Text(stringResource(R.string.cancel_request_button_yes))
                }
            },
            dismissButton = {
                Button(onClick = {
                    viewModel.onViewEvent(
                        ShelterRequestsEvents.ConfirmCancelClicked(false)
                    )
                }) {
                    Text(stringResource(R.string.cancel_cancel_request_no))
                }
            }
        )
    }
}

@Composable
private fun ShelterRequestCard(
    form: ShelterRequestsState.Form,
    onClick: () -> Unit,
    onHide: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),

        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            AnimalImage(
                Modifier
                    .size(72.dp)
                    .clip(CircleShape),
                form.photoUrls.firstOrNull(),
                ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = form.animalName,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = form.userName,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = when (form.status) {
                        AdoptionStatus.APPROVED ->
                            stringResource(R.string.adoption_status_approved)

                        AdoptionStatus.REJECTED ->
                            stringResource(R.string.adoption_status_rejected)

                        AdoptionStatus.PENDING ->
                            stringResource(R.string.adoption_status_pending)

                        AdoptionStatus.CANCELED ->
                            stringResource(R.string.adoption_status_canceled)

                        else -> ""
                    },
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (form.status.isFinal()) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onHide
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

        }
    }
}