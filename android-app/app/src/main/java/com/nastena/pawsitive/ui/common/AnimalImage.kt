package com.nastena.pawsitive.ui.common

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nastena.pawsitive.R

@Composable
fun AnimalImage(
    modifier: Modifier = Modifier,
    photoUrl: String?,
    contentScale: ContentScale = ContentScale.Fit
) {
    if (photoUrl == null) {
        Image(
            modifier = modifier,
            painter = painterResource(R.drawable.paw),
            contentScale = contentScale,
            contentDescription = null
        )
    } else {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(photoUrl)
                .build(),
            contentDescription = null,
            contentScale = contentScale,
            modifier = modifier,
            error = painterResource(R.drawable.ic_image_error)
        )
    }
}
