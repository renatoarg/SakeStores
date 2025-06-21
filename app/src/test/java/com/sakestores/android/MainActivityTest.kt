package com.sakestores.android

import org.junit.Test
import org.junit.Assert.*
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivityTest {

    @Test
    fun `URL encoding and decoding should work correctly`() {
        // Given
        val originalShopName = "Test Sake Shop & Co"

        // When
        val encodedName = URLEncoder.encode(originalShopName, StandardCharsets.UTF_8.toString())
        val decodedName = URLDecoder.decode(encodedName, StandardCharsets.UTF_8.toString())

        // Then
        assertNotEquals(originalShopName, encodedName) // deve ser diferente quando encoded
        assertEquals(originalShopName, decodedName) // deve voltar ao original
    }

    @Test
    fun `URL encoding should handle special characters`() {
        // Given
        val shopNames = listOf(
            "Shop with spaces",
            "Shop & Brewery",
            "Shop/Bar",
            "Shop+Restaurant",
            "日本酒店" // Japanese characters
        )

        shopNames.forEach { originalName ->
            // When
            val encoded = URLEncoder.encode(originalName, StandardCharsets.UTF_8.toString())
            val decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString())

            // Then
            assertEquals("Failed for: $originalName", originalName, decoded)
        }
    }

    @Test
    fun `empty shopName should decode to empty string`() {
        // Given
        val emptyName = ""

        // When
        val encoded = URLEncoder.encode(emptyName, StandardCharsets.UTF_8.toString())
        val decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString())

        // Then
        assertEquals("", decoded)
    }

    @Test
    fun `shopName with only spaces should be handled correctly`() {
        // Given
        val spacesOnly = "   "

        // When
        val encoded = URLEncoder.encode(spacesOnly, StandardCharsets.UTF_8.toString())
        val decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString())

        // Then
        assertEquals(spacesOnly, decoded)
    }

    @Test
    fun `shopName with special characters should be encoded differently`() {
        // Given
        val nameWithSpecialChars = "Sake Shop & Restaurant + Bar"

        // When
        val encoded = URLEncoder.encode(nameWithSpecialChars, StandardCharsets.UTF_8.toString())
        val decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString())

        // Then
        assertEquals(nameWithSpecialChars, decoded)
        assertNotEquals("Original and encoded should be different", nameWithSpecialChars, encoded)
        assertTrue("Should contain encoded characters", encoded.contains("%"))
    }

    @Test
    fun `shopName with percentage sign should be handled correctly`() {
        // Given - caso edge: nome que já contém %
        val nameWithPercent = "50% Off Sake Store"

        // When
        val encoded = URLEncoder.encode(nameWithPercent, StandardCharsets.UTF_8.toString())
        val decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString())

        // Then
        assertEquals(nameWithPercent, decoded)
        assertTrue("Should encode the % sign", encoded.contains("%25"))
    }
}