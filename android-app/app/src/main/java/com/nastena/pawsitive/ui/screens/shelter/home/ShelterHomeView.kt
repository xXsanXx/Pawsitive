package com.nastena.pawsitive.ui.screens.shelter.home

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
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
import com.nastena.pawsitive.ui.common.AnimalImage
import com.nastena.pawsitive.ui.common.PawsitiveTextButton
import com.nastena.pawsitive.ui.common.localization.LocalizationUtils

@Composable
fun ShelterHomeView(
    modifier: Modifier = Modifier,
    viewModel: ShelterHomeViewModel
) {

    val animalsState by viewModel.animalsState.collectAsState()
    val confirmAnimalDeleteState by viewModel.confirmAnimalDelete.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onViewEvent(ShelterHomeEvents.AddAnimalClicked)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }

    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {

            itemsIndexed(animalsState) { index, animal ->

                AnimalCard(
                    animal = animal,
                    onEdit = {
                        viewModel.onViewEvent(
                            ShelterHomeEvents.EditingClicked(index)
                        )
                    },
                    onDelete = {
                        viewModel.onViewEvent(
                            ShelterHomeEvents.RemoveClicked(index)
                        )
                    }
                )

            }

        }

    }

    if (confirmAnimalDeleteState?.isVisible == true) {

        AlertDialog(
            onDismissRequest = { viewModel.onConfirmDelete(false) },

            title = {
                Text(stringResource(R.string.remove_animal_title))
            },

            text = {
                Text(stringResource(R.string.warning_remove_animal))
            },

            confirmButton = {
                PawsitiveTextButton(
                    onClick = { viewModel.onConfirmDelete(true) }
                ) {
                    Text(stringResource(R.string.remove_animal_button))
                }
            },

            dismissButton = {
                PawsitiveTextButton(
                    onClick = { viewModel.onConfirmDelete(false) }
                ) {
                    Text(stringResource(R.string.cancel_remove_animal))
                }
            }
        )
    }
}

@Composable
private fun AnimalCard(
    animal: ShelterHomeState.Animal,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),

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

            verticalAlignment = Alignment.CenterVertically
        ) {

            AnimalImage(
                Modifier
                    .clip(CircleShape)
                    .size(72.dp),
                animal.photoUrls.firstOrNull(),
                ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = animal.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = stringResource(
                        LocalizationUtils.getAnimalTypeStringId(animal.type)
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "${animal.age} ${
                        stringResource(R.string.common_years)
                    }",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row {

                IconButton(onClick = onEdit) {

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )
                }

                IconButton(onClick = onDelete) {

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