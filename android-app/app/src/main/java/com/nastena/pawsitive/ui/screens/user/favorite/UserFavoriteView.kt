package com.nastena.pawsitive.ui.screens.user.favorite

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
fun UserFavoriteView(
    modifier: Modifier = Modifier,
    viewModel: UserFavoriteViewModel
) {

    val animalsState by viewModel.animalsState.collectAsState()
    val confirmAnimalDeleteState by viewModel.confirmAnimalDelete.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {

            item {
                Text(
                    text = stringResource(R.string.favorite_title),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }

            itemsIndexed(animalsState) { index, animal ->

                FavoriteAnimalCard(
                    animal = animal,
                    onGoTo = {
                        viewModel.onViewEvent(UserFavoriteEvents.GoToAnimalClicked(index))
                    },
                    onDelete = {
                        viewModel.onViewEvent(UserFavoriteEvents.RemoveClicked(index))
                    }
                )
            }
        }
    }

    if (confirmAnimalDeleteState?.isVisible == true) {

        AlertDialog(
            onDismissRequest = { viewModel.onConfirmDelete(false) },
            title = { Text(stringResource(R.string.favorite_remove_animal_title)) },
            text = { Text(stringResource(R.string.favorite_warning_remove_animal)) },
            confirmButton = {
                PawsitiveTextButton(onClick = { viewModel.onConfirmDelete(true) }) {
                    Text(stringResource(R.string.favorite_remove_animal_button))
                }
            },
            dismissButton = {
                PawsitiveTextButton(onClick = { viewModel.onConfirmDelete(false) }) {
                    Text(stringResource(R.string.favorite_cancel_remove_animal))
                }
            }
        )
    }
}


@Composable
private fun FavoriteAnimalCard(
    animal: UserFavoriteState.Animal,
    onGoTo: () -> Unit,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(
                onClick = onGoTo
            ),

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
                    .size(72.dp)
                    .clip(CircleShape),
                animal.photoUrl,
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
                    text = "${animal.age} ${stringResource(R.string.common_years)}",
                    style = MaterialTheme.typography.bodySmall
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