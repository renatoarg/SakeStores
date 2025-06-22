@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.sakestores.feat_sake_details

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.sakestores.feat_sake_details.ui.SakeShopDetailsContent
import org.junit.Rule
import org.junit.Test

/**
 * Test suite for the [SakeShopDetailsContent] composable within
 * a shared transition layout.
 *
 * This class contains UI tests verifying:
 * - Rendering of UI elements with expected content.
 * - Correct behavior of clickable buttons (maps, website).
 * - Handling of edge cases such as extreme or low rating values.
 * - Proper display of all textual components and their visibility.
 */
class SakeShopDetailsScreenTest {

    /** Compose test rule to set up and control Compose testing environment */
    @get:Rule
    val composeTestRule = createComposeRule()

    /**
     * Verifies that the [SakeShopDetailsContent] renders all expected
     * UI elements correctly given a sample sake shop.
     */
    @OptIn(ExperimentalSharedTransitionApi::class)
    @Test
    fun sakeShopDetailsContent_rendersCorrectly() {
        val sampleShop = com.sakestores.domain.model.SakeShop(
            name = "Test Detail Shop",
            description = "Amazing sake shop for details testing",
            picture = "", // Sem URL para evitar problemas de rede
            address = "123 Test Street, Tokyo",
            coordinates = listOf(35.6762, 139.6503),
            googleMapsLink = "https://maps.google.com/test",
            website = "https://test-shop.com",
            rating = 4.2f
        )

        var mapsClicked = false
        var websiteClicked = false
        var backClicked = false

        composeTestRule.setContent {
            SharedTransitionLayout {
                androidx.compose.animation.AnimatedVisibility(visible = true) {
                    SakeShopDetailsContent(
                        sakeShop = sampleShop,
                        onMapsClick = { mapsClicked = true },
                        onWebsiteClick = { websiteClicked = true },
                        onBackClick = { backClicked = true },
                        animatedVisibilityScope = this,
                        modifier = androidx.compose.ui.Modifier.testTag("details_content")
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("Test Detail Shop")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Amazing sake shop for details testing")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("4.2")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("123 Test Street, Tokyo")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Open in Maps")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Visit Website")
            .assertIsDisplayed()
    }

    /**
     * Tests button interactions on the content, ensuring
     * that clicking the maps and website buttons triggers
     * the appropriate callbacks with correct URLs.
     */
    @Test
    fun detailsContent_buttonInteractions() {
        val sampleShop = com.sakestores.domain.model.SakeShop(
            name = "Interactive Shop",
            description = "Test description for interactions",
            picture = "",
            address = "Interactive Address",
            coordinates = listOf(0.0, 0.0),
            googleMapsLink = "https://maps.google.com/interactive",
            website = "https://interactive-shop.com",
            rating = 3.9f
        )

        var mapsClicked = ""
        var websiteClicked = ""

        composeTestRule.setContent {
            SharedTransitionLayout {
                androidx.compose.animation.AnimatedVisibility(visible = true) {
                    SakeShopDetailsContent(
                        sakeShop = sampleShop,
                        onMapsClick = { url -> mapsClicked = url },
                        onWebsiteClick = { url -> websiteClicked = url },
                        onBackClick = { },
                        animatedVisibilityScope = this,
                        modifier = androidx.compose.ui.Modifier.testTag("interactive_content")
                    )
                }
            }
        }

        assert(mapsClicked.isEmpty()) { "Maps já foi clicado" }
        assert(websiteClicked.isEmpty()) { "Website já foi clicado" }

        composeTestRule.onNodeWithText("Open in Maps")
            .performClick()

        assert(mapsClicked == "https://maps.google.com/interactive") {
            "Expected maps URL, got '$mapsClicked'"
        }

        composeTestRule.onNodeWithText("Visit Website")
            .performClick()

        assert(websiteClicked == "https://interactive-shop.com") {
            "Expected website URL, got '$websiteClicked'"
        }
    }

    /**
     * Validates that the UI correctly displays extreme values
     * such as very long texts, boundary coordinates, and perfect rating.
     */
    @Test
    fun detailsContent_handlesExtremeValues() {
        val extremeShop = com.sakestores.domain.model.SakeShop(
            name = "Very Long Shop Name That Should Be Handled",
            description = "Very long description that should be handled gracefully",
            picture = "",
            address = "Very long address with multiple details",
            coordinates = listOf(-180.0, 90.0),
            googleMapsLink = "",
            website = "",
            rating = 5.0f
        )

        composeTestRule.setContent {
            SharedTransitionLayout {
                androidx.compose.animation.AnimatedVisibility(visible = true) {
                    SakeShopDetailsContent(
                        sakeShop = extremeShop,
                        onMapsClick = { },
                        onWebsiteClick = { },
                        onBackClick = { },
                        animatedVisibilityScope = this,
                        modifier = androidx.compose.ui.Modifier.testTag("extreme_content")
                    )
                }
            }
        }

        composeTestRule.onNodeWithTag("extreme_content")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("5.0")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("-180.0, 90.0", substring = true)
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Open in Maps")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Visit Website")
            .assertIsDisplayed()
    }

    /**
     * Tests UI with a sake shop having a low rating.
     */
    @Test
    fun detailsContent_lowRatingScenario() {
        val lowRatingShop = com.sakestores.domain.model.SakeShop(
            name = "Low Rating Shop",
            description = "Shop with low rating for testing",
            picture = "",
            address = "Low Rating Address, Tokyo",
            coordinates = listOf(0.0, 0.0),
            googleMapsLink = "https://maps.google.com/low",
            website = "https://low-rating.com",
            rating = 1.0f
        )

        composeTestRule.setContent {
            SharedTransitionLayout {
                androidx.compose.animation.AnimatedVisibility(visible = true) {
                    SakeShopDetailsContent(
                        sakeShop = lowRatingShop,
                        onMapsClick = { },
                        onWebsiteClick = { },
                        onBackClick = { },
                        animatedVisibilityScope = this,
                        modifier = androidx.compose.ui.Modifier.testTag("low_rating_content")
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("Low Rating Shop")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("1.0")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Low Rating Address, Tokyo")
            .assertIsDisplayed()
    }

    /**
     * Tests UI with a sake shop having a perfect rating.
     */
    @Test
    fun detailsContent_perfectRatingScenario() {
        val perfectShop = com.sakestores.domain.model.SakeShop(
            name = "Perfect Shop",
            description = "Perfect sake shop with highest rating",
            picture = "",
            address = "Perfect Address, Kyoto",
            coordinates = listOf(1.0, 1.0),
            googleMapsLink = "https://maps.google.com/perfect",
            website = "https://perfect.com",
            rating = 5.0f
        )

        composeTestRule.setContent {
            SharedTransitionLayout {
                androidx.compose.animation.AnimatedVisibility(visible = true) {
                    SakeShopDetailsContent(
                        sakeShop = perfectShop,
                        onMapsClick = { },
                        onWebsiteClick = { },
                        onBackClick = { },
                        animatedVisibilityScope = this,
                        modifier = androidx.compose.ui.Modifier.testTag("perfect_content")
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("Perfect Shop")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("5.0")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Perfect Address, Kyoto")
            .assertIsDisplayed()
    }

    /**
     * Exercises all text components and interactions in the UI,
     * verifying display and interaction callbacks for maps and website.
     */
    @Test
    fun detailsContent_exercisesAllComponents() {
        val fullShop = com.sakestores.domain.model.SakeShop(
            name = "Complete Test Shop",
            description = "Full description to exercise text components",
            picture = "",
            address = "Complete Address, Tokyo, Japan",
            coordinates = listOf(35.6762, 139.6503),
            googleMapsLink = "https://maps.google.com/complete",
            website = "https://complete-shop.com",
            rating = 3.7f
        )

        var mapsClicked = false
        var websiteClicked = false

        composeTestRule.setContent {
            SharedTransitionLayout {
                androidx.compose.animation.AnimatedVisibility(visible = true) {
                    SakeShopDetailsContent(
                        modifier = androidx.compose.ui.Modifier
                            .testTag("complete_content")
                            .fillMaxSize(),
                        sakeShop = fullShop,
                        onMapsClick = { mapsClicked = true },
                        onWebsiteClick = { websiteClicked = true },
                        onBackClick = { },
                        animatedVisibilityScope = this
                    )
                }
            }
        }


        composeTestRule.onNodeWithText("Complete Test Shop")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Full description to exercise text components")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("3.7")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Complete Address, Tokyo, Japan")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Open in Maps")
            .performClick()

        assert(mapsClicked) { "Maps click callback not triggered" }

        composeTestRule.onNodeWithText("Visit Website")
            .performClick()

        assert(websiteClicked) { "Website click callback not triggered" }
    }
}
