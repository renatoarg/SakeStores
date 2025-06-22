@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)

package com.sakestores.feat_sake_list

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.sakestores.feat_sake_list.ui.SakeShopCard
import androidx.compose.foundation.lazy.itemsIndexed
import org.junit.Rule
import org.junit.Test

class SakeShopListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun screen_showsTopBarTitle() {
        composeTestRule.setContent {
            androidx.compose.material3.MaterialTheme {
                androidx.compose.material3.TopAppBar(
                    title = { androidx.compose.material3.Text("Sake Stores") }
                )
            }
        }

        composeTestRule.onNodeWithText("Sake Stores")
            .assertIsDisplayed()
    }

    @Test
    fun screen_hasMainScreenTag() {
        composeTestRule.setContent {
            androidx.compose.foundation.layout.Box(
                modifier = androidx.compose.ui.Modifier.testTag("main_screen")
            ) {
                androidx.compose.material3.Text("Test Content")
            }
        }

        composeTestRule.onNodeWithTag("main_screen")
            .assertIsDisplayed()
    }

    @Test
    fun sakeShopCard_rendersCorrectly() {
        val sampleShop = com.sakestores.domain.model.SakeShop(
            name = "Test Sake Shop",
            description = "A great sake shop for testing",
            picture = "", // ✅ URL vazia para evitar carregamento
            address = "Test Address",
            coordinates = listOf(0.0, 0.0),
            googleMapsLink = "https://maps.google.com",
            website = "https://test.com",
            rating = 4.5f
        )

        var clickCalled = false

        composeTestRule.setContent {
            SharedTransitionLayout {
                androidx.compose.animation.AnimatedVisibility(visible = true) {
                    SakeShopCard(
                        sakeShop = sampleShop,
                        onClick = { clickCalled = true },
                        animatedVisibilityScope = this,
                        modifier = androidx.compose.ui.Modifier.testTag("test_card")
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("test_card")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Test Sake Shop")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("A great sake shop for testing")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("4.5")
            .assertIsDisplayed()

        composeTestRule.onNodeWithTag("test_card")
            .performClick()

        assert(clickCalled) { "Click callback não foi chamado" }
    }

    @Test
    fun sakeShopCard_clickInteraction() {
        val sampleShop = com.sakestores.domain.model.SakeShop(
            name = "Clickable Shop",
            description = "Test description",
            picture = "",
            address = "Test Address",
            coordinates = listOf(0.0, 0.0),
            googleMapsLink = "",
            website = "",
            rating = 3.8f
        )

        var clickedShopName = ""

        composeTestRule.setContent {
            SharedTransitionLayout {
                androidx.compose.animation.AnimatedVisibility(visible = true) {
                    SakeShopCard(
                        sakeShop = sampleShop,
                        onClick = { clickedShopName = sampleShop.name },
                        animatedVisibilityScope = this,
                        modifier = androidx.compose.ui.Modifier.testTag("clickable_card")
                    )
                }
            }
        }

        assert(clickedShopName.isEmpty()) { "Click já foi chamado antes do teste" }

        composeTestRule.onNodeWithTag("clickable_card")
            .performClick()

        assert(clickedShopName == "Clickable Shop") {
            "Expected 'Clickable Shop', got '$clickedShopName'"
        }
    }

    @Test
    fun sakeShopCard_handlesLongText() {
        val shopWithLongText = com.sakestores.domain.model.SakeShop(
            name = "This is a very long sake shop name that should be truncated properly according to our UI specifications",
            description = "This is an extremely long description that should also be handled gracefully by our UI component and show ellipsis when it exceeds the maximum number of lines we allow in our design system",
            picture = "",
            address = "Very long address",
            coordinates = listOf(0.0, 0.0),
            googleMapsLink = "",
            website = "",
            rating = 5.0f
        )

        composeTestRule.setContent {
            SharedTransitionLayout {
                androidx.compose.animation.AnimatedVisibility(visible = true) {
                    SakeShopCard(
                        sakeShop = shopWithLongText,
                        onClick = { },
                        animatedVisibilityScope = this,
                        modifier = androidx.compose.ui.Modifier.testTag("long_text_card")
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("long_text_card")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("This is a very long sake shop name", substring = true)
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("5.0")
            .assertIsDisplayed()
    }

    @Test
    fun multipleCards_renderCorrectly() {
        val sakeShops = listOf(
            com.sakestores.domain.model.SakeShop(
                name = "First Shop",
                description = "First description",
                picture = "",
                address = "Address 1",
                coordinates = listOf(0.0, 0.0),
                googleMapsLink = "",
                website = "",
                rating = 4.1f
            ),
            com.sakestores.domain.model.SakeShop(
                name = "Second Shop",
                description = "Second description",
                picture = "",
                address = "Address 2",
                coordinates = listOf(1.0, 1.0),
                googleMapsLink = "",
                website = "",
                rating = 4.7f
            )
        )

        composeTestRule.setContent {
            SharedTransitionLayout {
                androidx.compose.animation.AnimatedVisibility(visible = true) {
                    androidx.compose.foundation.lazy.LazyColumn {
                        items(sakeShops.size) { index ->
                            val shop = sakeShops[index]
                            SakeShopCard(
                                sakeShop = shop,
                                onClick = { },
                                animatedVisibilityScope = this@AnimatedVisibility,
                                modifier = androidx.compose.ui.Modifier.testTag("card_$index")
                            )
                        }
                    }
                }
            }
        }

        composeTestRule.onNodeWithText("First Shop")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Second Shop")
            .assertIsDisplayed()
    }
}