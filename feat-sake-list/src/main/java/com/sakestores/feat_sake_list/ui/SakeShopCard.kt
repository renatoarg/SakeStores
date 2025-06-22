package com.sakestores.feat_sake_list.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sakestores.design_system.components.NetworkImageWithLoading
import com.sakestores.design_system.theme.SakeStoresTheme
import com.sakestores.domain.model.SakeShop

/**
 * Composable displaying a card view for a single [SakeShop], including its image, name,
 * description, and rating.
 *
 * The card supports shared element transitions via [SharedTransitionScope] and
 * [AnimatedVisibilityScope] to enable smooth animations between screens.
 *
 * @param sakeShop The [SakeShop] data to display.
 * @param onClick Lambda invoked when the card is clicked.
 * @param animatedVisibilityScope The [AnimatedVisibilityScope] used for shared transition animation.
 * @param modifier [Modifier] for styling and layout adjustments.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SakeShopCard(
    sakeShop: SakeShop,
    onClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        // Store name
        Text(
            text = sakeShop.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(16.dp)
                .sharedElement(
                    state = rememberSharedContentState("text/${sakeShop.name}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        )
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // address
            Text(
                text = sakeShop.address,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            // star rating
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = sakeShop.rating.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
    }
}

/**
 * Preview of the [SakeShopCard] composable with sample data.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true, backgroundColor = 0xFFFFFBF5)
@Composable
private fun SakeShopCardPreview() {
    SakeStoresTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                this@SharedTransitionLayout.SakeShopCard(
                    sakeShop = sampleSakeShop(),
                    onClick = {  },
                    animatedVisibilityScope = this,
                    modifier = Modifier
                )
            }
        }
    }
}

/**
 * Provides sample [SakeShop] data for previews and tests.
 *
 * @return Sample [SakeShop] instance.
 */
private fun sampleSakeShop() = SakeShop(
    name = "Sake Store Tokyo",
    description = "Traditional Japanese sake store with premium selections from renowned breweries across Japan.",
    picture = "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400",
    address = "123 Street",
    coordinates = listOf(2.15, 4.35),
    googleMapsLink = "",
    website = "",
    rating = 4.8f
)
