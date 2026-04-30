package com.nastena.pawsitive.ui.screens.user.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.nastena.pawsitive.ui.theme.TextPrimary
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

    val animatedOffsetX by animateFloatAsState(offsetX)
    val rotation = animatedOffsetX / 40

    val swipeProgress = (animatedOffsetX / cardWidth).coerceIn(-1f, 1f)

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Card(
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .padding(top = 50.dp, start = 12.dp, end = 12.dp, bottom = 16.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.98f)
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
                        error = painterResource(R.drawable.ic_image_error)
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


                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = currentAnimalState.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )

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


        Text(
            text = "<- Свайп влево - пропустить | вправо - в избранное ->",
            color = TextPrimary,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 9.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color.Red.copy(alpha = 0.3f),
                            Color.Transparent,
                            Color.Green.copy(alpha = 0.3f)
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 0.5.dp,
                    color = Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 15.dp, vertical = 10.dp)
        )
    }
}
