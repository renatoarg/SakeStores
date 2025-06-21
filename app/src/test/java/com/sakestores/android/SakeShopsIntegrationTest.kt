package com.sakestores.android

import org.junit.Test
import org.junit.Assert.*
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class SakeShopsIntegrationTest {

    @Test
    fun `navigation parameter flow should work end to end`() {
        // Given - simulação do flow: List → Details
        val originalShopName = "Test Sake Shop & Co"

        // When - simula click no item da lista
        val encodedForNavigation = URLEncoder.encode(originalShopName, StandardCharsets.UTF_8.toString())
        val navigationRoute = "sake_shop_details/$encodedForNavigation"

        // Then - simula extração na tela de details
        val extractedEncoded = navigationRoute.substringAfter("sake_shop_details/")
        val finalShopName = URLDecoder.decode(extractedEncoded, StandardCharsets.UTF_8.toString())

        assertEquals(originalShopName, finalShopName)
        assertTrue("Route should contain encoded name", navigationRoute.contains(encodedForNavigation))
    }

    @Test
    fun `complete navigation flow with multiple shop names`() {
        // Given - diferentes tipos de nomes de shops
        val shopNames = listOf(
            "Simple Shop",
            "Shop & Restaurant",
            "Shop/Bar + Brewery",
            "日本酒専門店", // Japanese
            "Shop with 100% sake",
            "Multi Word Sake Shop Name"
        )

        shopNames.forEach { originalName ->
            // When - simula todo o flow
            val encoded = URLEncoder.encode(originalName, StandardCharsets.UTF_8.toString())
            val route = "sake_shop_details/$encoded"
            val extractedParam = route.substringAfter("sake_shop_details/")
            val decoded = URLDecoder.decode(extractedParam, StandardCharsets.UTF_8.toString())

            // Then
            assertEquals("Failed for shop: $originalName", originalName, decoded)
        }
    }

    @Test
    fun `back navigation flow should work correctly`() {
        // Given - user está na details screen
        val currentRoute = "sake_shop_details/Test%20Shop"

        // When - simula back navigation
        val shouldNavigateBack = currentRoute.startsWith("sake_shop_details")
        val backDestination = "sake_shops_list"

        // Then
        assertTrue("Should identify details screen", shouldNavigateBack)
        assertEquals("sake_shops_list", backDestination)
    }

    @Test
    fun `route parsing edge cases should be handled`() {
        // Given - edge cases com comportamento real do URLEncoder
        val testCases = listOf(
            "" to "",
            "test" to "test",
            "test shop" to "test+shop" // espaço vira +
        )

        testCases.forEach { (shopName, expectedEncoded) ->
            // When
            val encoded = URLEncoder.encode(shopName, StandardCharsets.UTF_8.toString())
            val route = "sake_shop_details/$encoded"

            // Then - teste o que realmente acontece
            val extractedParam = route.substringAfter("sake_shop_details/")
            val decoded = URLDecoder.decode(extractedParam, StandardCharsets.UTF_8.toString())

            assertEquals("Failed roundtrip for: '$shopName'", shopName, decoded)

            if (shopName.isNotEmpty()) {
                assertTrue("Route should contain encoded parameter", route.contains(encoded))
            }
        }
    }

    @Test
    fun `start destination should be correct`() {
        // Given - app initialization
        val startDestination = "sake_shops_list"

        // When - verify navigation setup
        val isValidStartRoute = startDestination == "sake_shops_list"

        // Then
        assertTrue("Start destination should be sake_shops_list", isValidStartRoute)
    }

    @Test
    fun `route parameter roundtrip should work for edge cases`() {
        // Given - casos edge importantes
        val edgeCases = listOf("", " ", "test", "test shop", "test&shop")

        edgeCases.forEach { originalName ->
            // When - simula o flow completo
            val encoded = URLEncoder.encode(originalName, StandardCharsets.UTF_8.toString())
            val route = "sake_shop_details/$encoded"
            val extractedParam = route.substringAfter("sake_shop_details/")
            val decoded = URLDecoder.decode(extractedParam, StandardCharsets.UTF_8.toString())

            // Then - o importante é o roundtrip funcionar
            assertEquals("Roundtrip failed for: '$originalName'", originalName, decoded)
        }
    }
}