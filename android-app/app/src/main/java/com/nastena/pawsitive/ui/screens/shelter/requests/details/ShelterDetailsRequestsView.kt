package com.nastena.pawsitive.ui.screens.shelter.requests.details

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.nastena.pawsitive.dto.AdoptionStatus
import com.nastena.pawsitive.ui.common.AnimalImage
import com.nastena.pawsitive.ui.common.Utils

@Composable
fun ShelterDetailsRequestsView(
    modifier: Modifier = Modifier,
    viewModel: ShelterDetailsRequestsViewModel
) {
    val formState by viewModel.formState.collectAsState()
    val dialogState by viewModel.confirmDialogState.collectAsState()


    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues: PaddingValues ->

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
                    text = stringResource(R.string.shelter_details_form),
                    style = MaterialTheme.typography.headlineMedium
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {

                    items(formState.photoUrls) { photo ->
                        AnimalImage(
                            Modifier
                                .size(300.dp)
                                .padding(horizontal = 8.dp),
                            photo
                        )
                    }
                }
            }

            item {

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        InfoRow(
                            icon = Icons.Default.Star,
                            title = stringResource(R.string.add_animal_name_label),
                            value = formState.animalName
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        InfoRow(
                            icon = Icons.Default.Person,
                            title = stringResource(R.string.user_profile_name),
                            value = formState.userName
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        InfoRow(
                            icon = Icons.Default.Cake,
                            title = stringResource(R.string.animal_birth_date),
                            Utils.formatDate(formState.birthDate)
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                        InfoRow(
                            icon = Icons.Default.Phone,
                            title = stringResource(R.string.phone_label),
                            value = formState.phone
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        InfoRow(
                            icon = Icons.Default.Work,
                            title = stringResource(R.string.profession_label),
                            value = formState.profession
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        InfoRow(
                            icon = Icons.Default.Pets,
                            title = stringResource(R.string.shelter_details_form_current_pets),
                            value = formState.currentPets
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        InfoRow(
                            icon = Icons.Default.History,
                            title = stringResource(R.string.shelter_details_form_previous_pets),
                            value = formState.previousPets
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        InfoRow(
                            icon = Icons.Default.Restaurant,
                            title = stringResource(R.string.shelter_details_form_feeding_experience),
                            value = formState.feedingExperience
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        InfoRow(
                            icon = Icons.Default.Vaccines,
                            title = stringResource(R.string.shelter_details_form_vaccination),
                            value = formState.vaccination
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        InfoRow(
                            icon = Icons.Default.QuestionAnswer,
                            title = stringResource(R.string.shelter_details_form_reason),
                            value = formState.reason
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        InfoRow(
                            icon = Icons.Default.Home,
                            title = stringResource(R.string.shelter_details_form_pet_care_when_away),
                            value = formState.petCareWhenAway
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        InfoRow(
                            icon = Icons.Default.Warning,
                            title = stringResource(R.string.shelter_details_form_problem_character),
                            value = formState.problemCharacter
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        InfoRow(
                            icon = Icons.Default.HealthAndSafety,
                            title = stringResource(R.string.shelter_details_form_health_issues),
                            value = formState.healthIssues
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                        InfoRow(
                            icon = Icons.Default.Info,
                            title = stringResource(R.string.shelter_details_form_additional_info),
                            value = formState.additionalInfo
                        )
                    }
                }
            }

            if (formState.status == AdoptionStatus.NONE || formState.status == AdoptionStatus.PENDING) {
                item {

                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Button(
                            onClick = {
                                viewModel.onViewEvent(
                                    ShelterDetailsRequestsEvents.ApprovedClicked
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.shelter_details_form_approved_button))
                        }

                        Button(
                            onClick = {
                                viewModel.onViewEvent(
                                    ShelterDetailsRequestsEvents.RejectedClicked
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.shelter_details_form_rejected_button))
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            } else {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = when (formState.status) {
                                AdoptionStatus.APPROVED -> stringResource(R.string.adoption_status_approved)
                                AdoptionStatus.REJECTED -> stringResource(R.string.adoption_status_rejected)
                                AdoptionStatus.CANCELED -> stringResource(R.string.adoption_status_canceled)
                                else -> ""
                            },
                            style = MaterialTheme.typography.bodyLarge,
                            color = when (formState.status) {
                                AdoptionStatus.APPROVED -> MaterialTheme.colorScheme.primary
                                AdoptionStatus.REJECTED -> MaterialTheme.colorScheme.error
                                AdoptionStatus.CANCELED -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.onSurface
                            }
                        )
                    }
                }
            }
        }

    }

    dialogState?.let { state ->

        AlertDialog(
            onDismissRequest = {
                viewModel.onConfirmDialogResult(false)
            },
            title = {
                Text(text = stringResource(R.string.shelter_details_form_submit))
            },
            text = {
                Text(
                    text = when (state.status) {
                        AdoptionStatus.APPROVED -> stringResource(R.string.shelter_details_form_question_approved)
                        AdoptionStatus.REJECTED -> stringResource(R.string.shelter_details_form_question_rejected)
                        else -> ""
                    }
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.onConfirmDialogResult(true)
                    }
                ) {
                    Text("Да")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.onConfirmDialogResult(false)
                    }
                ) {
                    Text("Нет")
                }
            }
        )
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}