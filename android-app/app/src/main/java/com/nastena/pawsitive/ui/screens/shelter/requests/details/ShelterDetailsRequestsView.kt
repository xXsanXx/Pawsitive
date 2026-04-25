package com.nastena.pawsitive.ui.screens.shelter.requests.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nastena.pawsitive.R
import com.nastena.pawsitive.dto.AdoptionStatus
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
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(formState.photoUrls) { photos ->

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(photos)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp),
                            error = painterResource(R.drawable.ic_image_error)
                        )
                    }
                }
            }

            item {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(text = formState.animalName)


                    Text(
                        text = stringResource(R.string.label_fio, formState.userName)

                    )

                    Text(
                        text = stringResource(
                            R.string.animal_birth_date,
                            Utils.formatDate(formState.birthDate),
                        )
                    )

                    Text(
                        text = stringResource(R.string.label_phone, formState.phone)
                    )

                    Text(
                        text = stringResource(R.string.label_profession, formState.profession)
                    )




                    Button(
                        onClick = {
                            viewModel.onViewEvent(
                                ShelterDetailsRequestsEvents.ApprovedClicked
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(R.string.shelter_details_form_approved_button))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.onViewEvent(
                                ShelterDetailsRequestsEvents.RejectedClicked
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = stringResource(R.string.shelter_details_form_rejected_button))
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
                            viewModel.onConfirmDialogResult(false)
                        }
                    ) {
                        Text("Нет")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            viewModel.onConfirmDialogResult(true)
                        }
                    ) {
                        Text("Да")
                    }
                }
            )
        }


    }


}