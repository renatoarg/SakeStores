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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.sakestores.design_system.R.*
import com.sakestores.design_system.components.NetworkImageWithLoading
import com.sakestores.design_system.theme.SakeStoresTheme
import com.sakestores.domain.model.SakeShop

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.SakeShopCard(
    sakeShop: SakeShop,
    onClick: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clickable { onClick() },
    ) {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(sakeShop.picture)
//                .crossfade(true)
//                .placeholder(drawable.placeholder2)
//                .memoryCachePolicy(CachePolicy.ENABLED)
//                .diskCachePolicy(CachePolicy.ENABLED)
//                .fallback(drawable.placeholder2)
//                .error(drawable.placeholder2)
//                .build(),
//            contentDescription = sakeShop.name,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(160.dp)
//                .padding(horizontal = 16.dp)
//                .sharedElement(
//                    state = rememberSharedContentState("image/${sakeShop.name}"),
//                    animatedVisibilityScope = animatedVisibilityScope
//                )
//                .background(MaterialTheme.colorScheme.surfaceVariant),
//            contentScale = ContentScale.Crop
//        )
        NetworkImageWithLoading(
            imageUrl = sakeShop.picture,
            contentDescription = sakeShop.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                            .sharedElement(
                    state = rememberSharedContentState("image/${sakeShop.name}"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        )
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    text = sakeShop.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

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
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
    }
}

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
                    animatedVisibilityScope = this
                )
            }
        }
    }
}

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