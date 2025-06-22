@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.sakestores.feat_sake_details.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sakestores.design_system.components.NetworkImageWithLoading
import com.sakestores.design_system.theme.SakeSecondary
import com.sakestores.design_system.theme.SakeStoresTheme
import com.sakestores.domain.model.SakeShop

/**
 * Composable function that displays detailed content for a [SakeShop] within a shared transition scope.
 *
 * This layout includes:
 * - A header image with shared transition effect.
 * - Sake shop name with shared transition effect.
 * - Rating display with star icon.
 * - Description of the sake shop.
 * - Address section with location icon and coordinates.
 * - Action buttons for opening location in maps and visiting the website.
 *
 * @param sakeShop The data model representing the sake shop details to display.
 * @param modifier Optional [Modifier] for styling this composable.
 * @param onMapsClick Lambda called with the Google Maps URL when the "Open in Maps" button is clicked.
 * @param onWebsiteClick Lambda called with the website URL when the "Visit Website" button is clicked.
 * @param animatedVisibilityScope The [AnimatedVisibilityScope] used for shared transition animation.
 * @param onBackClick Lambda called when a back action is triggered (not used in the current UI).
 */
@Composable
fun SharedTransitionScope.SakeShopDetailsContent(
    sakeShop: SakeShop,
    modifier: Modifier = Modifier,
    onMapsClick: (String) -> Unit = {},
    onWebsiteClick: (String) -> Unit = {},
    animatedVisibilityScope: AnimatedVisibilityScope,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Image Card
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            NetworkImageWithLoading(
                imageUrl = sakeShop.picture,
                contentDescription = sakeShop.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .sharedElement(
                        state = rememberSharedContentState("image/${sakeShop.name}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = sakeShop.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .sharedElement(
                        state = rememberSharedContentState("text/${sakeShop.name}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = SakeSecondary,
                        modifier = Modifier.size(30.dp)
                    )
                    Text(
                        text = sakeShop.rating.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    text = sakeShop.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Address Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { onMapsClick(sakeShop.googleMapsLink) },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Address",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = sakeShop.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { onWebsiteClick(sakeShop.website) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Visit Website")
            }
        }
    }
}

/**
 * Preview of [SakeShopDetailsContent] in light theme with sample data.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFBF5)
@Composable
private fun SakeShopDetailsContentPreview() {
    SakeStoresTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SharedTransitionLayout {
                var isVisible by remember { mutableStateOf(true) }

                AnimatedVisibility(visible = isVisible) {
                    this@SharedTransitionLayout.SakeShopDetailsContent(
                        sakeShop = sampleDetailedSakeShop(),
                        modifier = Modifier.padding(16.dp),
                        onMapsClick = { println("Maps clicked: $it") },
                        onWebsiteClick = { println("Website clicked: $it") },
                        animatedVisibilityScope = this,
                        onBackClick = {  }
                    )
                }
            }
        }
    }
}

/**
 * Preview of [SakeShopDetailsContent] in dark theme with sample data.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFBF5)
@Composable
private fun SakeShopDetailsContentDarkPreview() {
    SakeStoresTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SharedTransitionLayout {
                var isVisible by remember { mutableStateOf(true) }

                AnimatedVisibility(visible = isVisible) {
                    this@SharedTransitionLayout.SakeShopDetailsContent(
                        sakeShop = sampleDetailedSakeShop(),
                        modifier = Modifier.padding(16.dp),
                        onMapsClick = { println("Maps clicked: $it") },
                        onWebsiteClick = { println("Website clicked: $it") },
                        animatedVisibilityScope = this,
                        onBackClick = {}
                    )
                }
            }
        }
    }
}

/**
 * Preview of [SakeShopDetailsContent] with long text description and light theme.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFBF5, heightDp = 800)
@Composable
private fun SakeShopDetailsContentLongTextPreview() {
    SakeStoresTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SharedTransitionLayout {
                var isVisible by remember { mutableStateOf(true) }

                AnimatedVisibility(visible = isVisible) {
                    this@SharedTransitionLayout.SakeShopDetailsContent(
                        sakeShop = sampleDetailedSakeShopLongText(),
                        modifier = Modifier.padding(16.dp),
                        onMapsClick = { println("Maps clicked: $it") },
                        onWebsiteClick = { println("Website clicked: $it") },
                        animatedVisibilityScope = this,
                        onBackClick = {}
                    )
                }
            }
        }
    }
}

/**
 * Compact width preview of [SakeShopDetailsContent].
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFBF5, widthDp = 320, heightDp = 600)
@Composable
private fun SakeShopDetailsContentCompactPreview() {
    SakeStoresTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SharedTransitionLayout {
                var isVisible by remember { mutableStateOf(true) }

                AnimatedVisibility(visible = isVisible) {
                    this@SharedTransitionLayout.SakeShopDetailsContent(
                        sakeShop = sampleDetailedSakeShop(),
                        modifier = Modifier.padding(8.dp),
                        onMapsClick = { println("Maps clicked: $it") },
                        onWebsiteClick = { println("Website clicked: $it") },
                        animatedVisibilityScope = this,
                        onBackClick = {}
                    )
                }
            }
        }
    }
}

/**
 * Provides a sample [SakeShop] instance with typical data.
 */
private fun sampleDetailedSakeShop(): SakeShop = SakeShop(
    name = "Sake House Tokyo",
    picture = "https://example.com/sakehouse.jpg",
    description = "Authentic sake experience from the heart of Tokyo with a wide selection and expert guidance.",
    rating = 4.8f,
    address = "123 Tokyo Street, Tokyo, Japan",
    coordinates = listOf(35.6895, 139.6917),
    googleMapsLink = "https://maps.google.com/?q=35.6895,139.6917",
    website = "https://sakehousetokyo.example.com"
)

/**
 * Provides a sample [SakeShop] with an extended long description for testing scrolling.
 */
private fun sampleDetailedSakeShopLongText(): SakeShop = sampleDetailedSakeShop().copy(
    description = """
        Authentic sake experience from the heart of Tokyo with a wide selection and expert guidance.
        Our sake is sourced from the finest breweries, featuring rare and exclusive labels.
        Enjoy tasting sessions, sake pairing with traditional dishes, and cultural events hosted regularly.
        Whether you are a beginner or a connoisseur, Sake House Tokyo offers a unique and memorable journey.
        Visit us to explore the rich heritage of sake craftsmanship and modern innovations in brewing.
    """.trimIndent()
)
