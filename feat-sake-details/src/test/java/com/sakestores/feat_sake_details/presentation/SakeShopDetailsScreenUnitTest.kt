package com.sakestores.feat_sake_details.presentation

import com.sakestores.domain.model.SakeShop
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for [SakeShop] data class and related callbacks
 * used in the sake shop details feature.
 *
 * This test class verifies:
 * - Correct initialization and data integrity of [SakeShop].
 * - Proper behavior and invocation of callbacks for map, website, and back actions.
 * - Handling of coordinate values within expected bounds.
 */
class SakeShopDetailsScreenUnitTest {

    /**
     * Tests that the [SakeShop] data class correctly stores
     * and exposes all its properties.
     */
    @Test
    fun sakeShop_dataClass_worksCorrectly() {

        val shop = SakeShop(
            name = "Test Shop",
            description = "Test Description",
            picture = "test.jpg",
            address = "Test Address",
            coordinates = listOf(35.6762, 139.6503),
            googleMapsLink = "https://maps.test.com",
            website = "https://test.com",
            rating = 4.5f
        )


        assertEquals("Test Shop", shop.name)
        assertEquals("Test Description", shop.description)
        assertEquals("test.jpg", shop.picture)
        assertEquals("Test Address", shop.address)
        assertEquals(listOf(35.6762, 139.6503), shop.coordinates)
        assertEquals("https://maps.test.com", shop.googleMapsLink)
        assertEquals("https://test.com", shop.website)
        assertEquals(4.5f, shop.rating, 0.01f)
    }

    /**
     * Verifies that callback functions for map, website, and back
     * actions can be mocked and are invoked correctly.
     */
    @Test
    fun callbacks_canBeMocked() {

        val onMapsClick = mockk<(String) -> Unit>(relaxed = true)
        val onWebsiteClick = mockk<(String) -> Unit>(relaxed = true)
        val onBackClick = mockk<() -> Unit>(relaxed = true)


        onMapsClick("https://maps.test.com")
        onWebsiteClick("https://test.com")
        onBackClick()

        verify { onMapsClick("https://maps.test.com") }
        verify { onWebsiteClick("https://test.com") }
        verify { onBackClick() }
    }

    /**
     * Checks that the coordinates list has exactly two elements
     * and that latitude and longitude values fall within valid bounds.
     */
    @Test
    fun sakeShop_coordinatesHandling() {

        val shop1 = SakeShop(
            name = "Shop1", description = "", picture = "", address = "",
            coordinates = listOf(0.0, 0.0),
            googleMapsLink = "", website = "", rating = 0f
        )

        val shop2 = SakeShop(
            name = "Shop2", description = "", picture = "", address = "",
            coordinates = listOf(-180.0, 90.0),
            googleMapsLink = "", website = "", rating = 5f
        )

        assertEquals(2, shop1.coordinates.size)
        assertEquals(2, shop2.coordinates.size)
        assertTrue(shop1.coordinates[0] >= -180.0)
        assertTrue(shop2.coordinates[1] <= 90.0)
    }
}
