package com.sakestores.design_system.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.sakestores.design_system.R

/**
 * A composable that displays an image from a network URL with a loading indicator.
 *
 * This component shows a circular progress indicator while the image is loading.
 * It uses Coil's [AsyncImage] to asynchronously load the image and provides
 * a placeholder, error, and fallback drawable resource.
 *
 * @param imageUrl The URL of the image to load. Can be null.
 * @param contentDescription Description of the image for accessibility.
 * @param modifier Optional [Modifier] for styling and layout adjustments.
 * @param contentScale Controls how the image should be scaled inside its bounds.
 *                     Defaults to [ContentScale.Crop].
 * @param placeholderRes Resource ID of the placeholder drawable shown if the image
 *                       fails to load or is null. Defaults to [R.drawable.placeholder2].
 */
@Composable
fun NetworkImageWithLoading(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderRes: Int = R.drawable.placeholder2
) {
    var isLoading by remember { mutableStateOf(true) }

    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .error(placeholderRes)
                .fallback(placeholderRes)
                .build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier.matchParentSize(),
            onState = { state ->
                isLoading = state is AsyncImagePainter.State.Loading
            }
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(32.dp),
                strokeWidth = 2.dp
            )
        }
    }
}
