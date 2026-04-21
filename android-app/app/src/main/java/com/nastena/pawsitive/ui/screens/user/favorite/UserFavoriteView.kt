package com.nastena.pawsitive.ui.screens.user.favorite

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
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
                    text = stringResource(R.string.favorite_title),
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            items(animalsState.size) { index: Int ->
                val animalState: UserFavoriteState.Animal = animalsState[index]

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    if (animalState.photoUrl != null) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(animalState.photoUrl)
                                .transformations(CircleCropTransformation())
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            error = painterResource(R.drawable.ic_image_error)
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.Start
                    ) {

                        Text(text = animalState.name)
                        Text(
                            text = stringResource(
                                LocalizationUtils.getAnimalTypeStringId(animalState.type)
                            )
                        )
                        Text("${animalState.age} ${stringResource(R.string.common_years)}")
                    }
                    IconButton(
                        onClick = {
                            viewModel.onViewEvent(UserFavoriteEvents.RemoveClicked(index))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.onViewEvent(UserFavoriteEvents.GoToAnimalClicked(index))
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
    if (confirmAnimalDeleteState?.isVisible == true) {
        AlertDialog(
            onDismissRequest = { viewModel.onConfirmDelete(false) },
            title = { Text(stringResource(R.string.favorite_remove_animal_title)) },
            text = { Text(stringResource(R.string.favorite_warning_remove_animal)) },
            confirmButton = {
                TextButton(onClick = { viewModel.onConfirmDelete(false) }) {
                    Text(stringResource(R.string.favorite_cancel_remove_animal))
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onConfirmDelete(true) }) {
                    Text(stringResource(R.string.favorite_remove_animal_button))
                }
            }
        )
    }


}