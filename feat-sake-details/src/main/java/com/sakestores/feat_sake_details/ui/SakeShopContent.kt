@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.sakestores.feat_sake_details.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
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
import com.sakestores.design_system.theme.SakeSecondary
import com.sakestores.design_system.theme.SakeStoresTheme
import com.sakestores.domain.model.SakeShop

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
            AsyncImage(
                model = sakeShop.picture,
                contentDescription = sakeShop.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .sharedElement(
                        state = rememberSharedContentState("image/${sakeShop.name}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
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
                .padding(horizontal = 16.dp),
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

                Text(
                    text = "Coordinates: ${sakeShop.coordinates[0]}, ${sakeShop.coordinates[1]}",
                    style = MaterialTheme.typography.bodySmall,
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
            Button(
                onClick = { onMapsClick(sakeShop.googleMapsLink) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Open in Maps")
            }

            OutlinedButton(
                onClick = { onWebsiteClick(sakeShop.website) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Visit Website")
            }
        }
    }
}

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

private fun sampleDetailedSakeShop() = SakeShop(
    name = "Hakutsuru Sake Museum Store",
    picture = "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=800",
    description = "Experience authentic Japanese sake culture at our traditional store located in the heart of Kyoto. We offer a carefully curated selection of premium sake from renowned breweries across Japan, featuring both traditional and modern brewing techniques.",
    rating = 4.8f,
    address = "2-1-1 Nishiki, Naka-ku, Kyoto 604-8031, Japan",
    coordinates = listOf(35.0042, 135.7695),
    googleMapsLink = "https://maps.google.com/?q=35.0042,135.7695",
    website = "https://hakutsuru-sake.com"
)

private fun sampleDetailedSakeShopLongText() = SakeShop(
    name = "Traditional Artisan Sake Brewery & Heritage Store with Centuries of History",
    picture = "https://images.unsplash.com/photo-1569880153113-3b31f8ad7bbc?w=800",
    description = "Our family-owned sake brewery has been crafting exceptional sake for over 400 years, passing down traditional brewing techniques through 15 generations. Located in the historic district of Fushimi, we specialize in junmai daiginjo and premium aged sake varieties. Our store features a tasting room where visitors can experience the full range of our sake portfolio, guided by our master brewers who share the secrets and stories behind each unique blend. We also offer traditional sake ceremony experiences and educational workshops about the intricate process of sake brewing, from rice selection to fermentation and aging.",
    rating = 4.9f,
    address = "123-45 Fushimi Momoyama-cho, Fushimi-ku, Kyoto 612-8039, Japan - Traditional Sake District, Historical Preservation Area",
    coordinates = listOf(34.9388, 135.7647),
    googleMapsLink = "https://maps.google.com/?q=34.9388,135.7647",
    website = "https://traditional-artisan-sake-brewery.co.jp"
)

private fun sampleDetailedSakeShop(
    name: String = "Sample Sake Store",
    rating: Float = 4.5f,
    description: String = "A wonderful sake store with great selection.",
    address: String = "Tokyo, Japan",
    imageUrl: String = "https://images.unsplash.com/photo-1578418166097-fe11b13f8c34?w=800"
) = SakeShop(
    name = name,
    picture = imageUrl,
    description = description,
    rating = rating,
    address = address,
    coordinates = listOf(35.6762, 139.6503),
    googleMapsLink = "https://maps.google.com/?q=35.6762,139.6503",
    website = "https://example-sake-store.com"
)