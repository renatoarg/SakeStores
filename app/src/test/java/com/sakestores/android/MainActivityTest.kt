package com.sakestores.android

import org.junit.Test
import org.junit.Assert.*
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Unit tests for verifying proper encoding and decoding of shop names
 * using [URLEncoder] and [URLDecoder].
 *
 * These tests ensure consistent and correct behavior when passing shop names
 * between screens via URL-safe strings in navigation routes.
 */
class MainActivityTest {

    /**
     * Verifies that encoding and decoding a string returns the original value.
     * Also checks that the encoded string differs from the original when it contains special characters.
     */
    @Test
    fun `URL encoding and decoding should work correctly`() {
        // Given
        val originalShopName = "Test Sake Shop & Co"

        // When
        val encodedName = URLEncoder.encode(originalShopName, StandardCharsets.UTF_8.toString())
        val decodedName = URLDecoder.decode(encodedName, StandardCharsets.UTF_8.toString())

        // Then
        assertNotEquals(originalShopName, encodedName)
        assertEquals(originalShopName, decodedName)
    }

    /**
     * Verifies that encoding and decoding handles various special characters correctly,
     * including spaces, ampersands, slashes, plus signs, and Unicode characters.
     */
    @Test
    fun `URL encoding should handle special characters`() {
        // Given
        val shopNames = listOf(
            "Shop with spaces",
            "Shop & Brewery",
            "Shop/Bar",
            "Shop+Restaurant",
            "日本酒店"
        )

        shopNames.forEach { originalName ->
            // When
            val encoded = URLEncoder.encode(originalName, StandardCharsets.UTF_8.toString())
            val decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString())

            // Then
            assertEquals("Failed for: $originalName", originalName, decoded)
        }
    }

    /**
     * Verifies that encoding and decoding an empty string returns an empty string.
     */
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

    /**
     * Verifies that a string containing only spaces is correctly encoded and decoded without data loss.
     */
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

    /**
     * Verifies that a shop name with special characters is encoded into a different string
     * and that the encoded version contains percent signs, indicating proper URL encoding.
     */
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

    /**
     * Verifies that a shop name containing a percentage sign (%) is properly encoded as "%25"
     * and successfully decoded back to its original form.
     */
    @Test
    fun `shopName with percentage sign should be handled correctly`() {
        // Given
        val nameWithPercent = "50% Off Sake Store"

        // When
        val encoded = URLEncoder.encode(nameWithPercent, StandardCharsets.UTF_8.toString())
        val decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8.toString())

        // Then
        assertEquals(nameWithPercent, decoded)
        assertTrue("Should encode the % sign", encoded.contains("%25"))
    }
}