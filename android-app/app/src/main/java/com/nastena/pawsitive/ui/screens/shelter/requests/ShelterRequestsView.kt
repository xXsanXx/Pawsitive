package com.nastena.pawsitive.ui.screens.shelter.requests

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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

@Composable
fun ShelterRequestsView(
    modifier: Modifier = Modifier,
    viewModel: ShelterRequestsViewModel
) {
    val form by viewModel.formState.collectAsState()


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
                    text = stringResource(R.string.shelter_forms_title),
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            items(form.size) { index: Int ->
                val formState: ShelterRequestsState.Form = form[index]

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    AnimalImage(
                        Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        formState.photoUrls.firstOrNull(),
                        ContentScale.Crop
                    )

                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.Start
                    ) {

                        Text(
                            text = formState.animalName,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = formState.userName,
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Text(
                            text = when (formState.status) {
                                AdoptionStatus.APPROVED -> stringResource(R.string.adoption_status_approved)
                                AdoptionStatus.REJECTED -> stringResource(R.string.adoption_status_rejected)
                                AdoptionStatus.PENDING -> stringResource(R.string.adoption_status_pending)
                                else -> "Нет"
                            }
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.onViewEvent(ShelterRequestsEvents.GoToFormClicked(index))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null
                        )
                    }

                }
            }
        }
    }
}