package com.nastena.pawsitive.ui.screens.user.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.nastena.pawsitive.dto.ShelterAnimalResponse

@Composable
fun UserHomeView(
    modifier: Modifier = Modifier,
    viewModel: UserHomeViewModel
) {
    val currentAnimalState: ShelterAnimalResponse? by viewModel.currentAnimalState.collectAsState()

    UserHomeView(
        modifier = modifier,
        currentAnimalState = currentAnimalState,
        onViewEvent = { event -> viewModel.onViewEvent(event) }
    )
}

@Composable
private fun UserHomeView(
    modifier: Modifier = Modifier,
    currentAnimalState: ShelterAnimalResponse?,
    onViewEvent: (UserHomeEvents) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = currentAnimalState?.name ?: "Животное не найдено",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        AnimalImage(animal = currentAnimalState)

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onViewEvent(UserHomeEvents.DislikeClicked) }) {
                Text("Пропустить")
            }

            Button(onClick = { onViewEvent(UserHomeEvents.DetailsClicked) }) {
                Text("Подробнее")
            }

            Button(onClick = { onViewEvent(UserHomeEvents.LikeClicked) }) {
                Text("Лайк")
            }
        }

    }

}

@Composable
fun AnimalImage(animal: ShelterAnimalResponse?) {
    val imageUrl = animal?.animalPhotos?.firstOrNull()
    if (imageUrl != null) {
        Image(
            painter = rememberAsyncImagePainter(
                model = imageUrl
            ),
            contentDescription = "Фото животного",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp))
        )
    } else {
        Text(
            text = "Фото недоступно",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
