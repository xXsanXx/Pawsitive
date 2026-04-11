package com.nastena.pawsitive.ui.screens.user.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nastena.pawsitive.R

@Composable
fun UserHomeView(
    modifier: Modifier = Modifier,
    viewModel: UserHomeViewModel
) {
    val currentAnimalState: UserHomeState.Animal? by viewModel.currentAnimalState.collectAsState()

    UserHomeView(
        modifier = modifier,
        currentAnimalState = currentAnimalState,
        onViewEvent = { event -> viewModel.onViewEvent(event) }
    )
}

@Composable
private fun UserHomeView(
    modifier: Modifier = Modifier,
    currentAnimalState: UserHomeState.Animal?,
    onViewEvent: (UserHomeEvents) -> Unit
) {

    if (currentAnimalState == null) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.no_animal)
            )

        }

        return;
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            if (currentAnimalState.photoUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(currentAnimalState.photoUrl)
                        .build(),
                    contentDescription = null,
                    error = painterResource(R.drawable.ic_image_error)
                )
            } else {
                Text(
                    text = stringResource(R.string.user_home_animal_no_photo)
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { onViewEvent(UserHomeEvents.DislikeClicked) }) {
                    Icon(
                        contentDescription = null,
                        imageVector = Icons.Default.ThumbDown
                    )
                }

                IconButton(onClick = { onViewEvent(UserHomeEvents.DetailsClicked) }) {
                    Icon(
                        contentDescription = null,
                        imageVector = Icons.Default.MoreVert
                    )
                }

                IconButton(onClick = { onViewEvent(UserHomeEvents.LikeClicked) }) {
                    Icon(
                        contentDescription = null,
                        imageVector = Icons.Default.ThumbUp
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(
                text = currentAnimalState.name,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}


