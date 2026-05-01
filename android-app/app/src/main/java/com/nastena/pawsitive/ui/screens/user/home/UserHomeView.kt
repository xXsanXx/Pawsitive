package com.nastena.pawsitive.ui.screens.user.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nastena.pawsitive.R
import com.nastena.pawsitive.ui.common.localization.LocalizationUtils
import kotlinx.coroutines.delay
import kotlin.math.roundToInt


@Composable
fun UserHomeView(
    modifier: Modifier = Modifier,
    viewModel: UserHomeViewModel
) {

    val currentAnimalState by viewModel.currentAnimalState.collectAsState()

    UserHomeView(
        modifier = modifier,
        currentAnimalState = currentAnimalState,
        onViewEvent = { viewModel.onViewEvent(it) }
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
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.no_animal))
        }
        return
    }

    var offsetX by remember { mutableFloatStateOf(0f) }
    var cardWidth by remember { mutableFloatStateOf(1f) }

    var showHint by remember { mutableStateOf(true) }

    val animatedOffsetX by animateFloatAsState(offsetX)
    val rotation = animatedOffsetX / 40
    val swipeProgress = (animatedOffsetX / cardWidth).coerceIn(-1f, 1f)

    LaunchedEffect(Unit) {
        offsetX = 150f
        delay(250)
        offsetX = -150f
        delay(250)
        offsetX = 0f
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Card(
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .padding(top = 50.dp, start = 12.dp, end = 12.dp, bottom = 100.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .onSizeChanged {
                    cardWidth = it.width.toFloat()
                }
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                .graphicsLayer {
                    rotationZ = rotation
                    val scale = 1f - kotlin.math.abs(swipeProgress) * 0.05f
                    scaleX = scale
                    scaleY = scale
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount.x
                        },
                        onDragEnd = {
                            val threshold = cardWidth * 0.25f

                            when {
                                offsetX > threshold -> {
                                    onViewEvent(UserHomeEvents.LikeClicked)
                                }

                                offsetX < -threshold -> {
                                    onViewEvent(UserHomeEvents.DislikeClicked)
                                }

                                else -> offsetX = 0f
                            }

                            offsetX = 0f
                        }
                    )
                }
        ) {

            Box {
                if (currentAnimalState.photoUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(currentAnimalState.photoUrl)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.paw)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.paw),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(48.dp),
                        contentScale = ContentScale.Fit
                    )
                }


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            when {
                                swipeProgress > 0 ->
                                    Color.Green.copy(alpha = swipeProgress * 0.25f)

                                swipeProgress < 0 ->
                                    Color.Red.copy(alpha = -swipeProgress * 0.25f)

                                else -> Color.Transparent
                            }
                        )
                )


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {

                    Text(
                        text = currentAnimalState.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                    )

                    Text(
                        text = stringResource(
                            LocalizationUtils.getAnimalTypeStringId(currentAnimalState.type)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )

                    Text(
                        text = stringResource(
                            LocalizationUtils.getAnimalGenderStringId(currentAnimalState.gender)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )

                    Text(
                        text = stringResource(
                            LocalizationUtils.getAnimalBreedStringId(currentAnimalState.breed)
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }


                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(
                            Color.Black.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(50)
                        )
                ) {
                    IconButton(
                        onClick = { onViewEvent(UserHomeEvents.DetailsClicked) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            IconButton(
                onClick = { onViewEvent(UserHomeEvents.DislikeClicked) },
                modifier = Modifier
                    .background(Color.Red.copy(alpha = 0.2f), RoundedCornerShape(50))
            ) {
                Icon(Icons.Default.Close, null)
            }

            IconButton(
                onClick = { onViewEvent(UserHomeEvents.LikeClicked) },
                modifier = Modifier
                    .background(Color.Green.copy(alpha = 0.2f), RoundedCornerShape(50))
            ) {
                Icon(Icons.Default.Favorite, null)
            }
        }

        if (showHint) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .pointerInput(Unit) {
                        detectTapGestures {
                            showHint = false
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.hint),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
