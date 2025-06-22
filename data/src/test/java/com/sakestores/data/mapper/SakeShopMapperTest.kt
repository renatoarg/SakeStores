package com.sakestores.data.mapper

import com.sakestores.data.dto.SakeShopDto
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for SakeShop mapping functions.
 *
 * These tests verify the correct conversion of [SakeShopDto] objects
 * to domain model [SakeShop] objects using the extension functions
 * defined in the mapper.
 */
class SakeShopMapperTest {

    private val mockedDto = SakeShopDto(
        name = "Mocked Sake Shop",
        description = "Mocked description",
        picture = "https://mock.com/mock.jpg",
        rating = 4.5f,
        address = "123 Mockled Street",
        coordinates = listOf(35.6762, 139.6503),
        googleMapsLink = "https://maps.google.com/mock",
        website = "https://mock-sake-shop.com"
    )

    @Test
    fun `toDomain should convert SakeShopDto to SakeShop correctly`() {
        // When
        val result = mockedDto.toDomain()

        // Then
        assertEquals("Mocked Sake Shop", result.name)
        assertEquals("Mocked description", result.description)
        assertEquals("https://mock.com/mock.jpg", result.picture)
        assertEquals(4.5f, result.rating, 0.01f)
        assertEquals("123 Mockled Street", result.address)
        assertEquals(listOf(35.6762, 139.6503), result.coordinates)
        assertEquals("https://maps.google.com/mock", result.googleMapsLink)
        assertEquals("https://mock-sake-shop.com", result.website)
    }

    @Test
    fun `toDomain should convert list of SakeShopDto to list of SakeShop correctly`() {
        // Given
        val dtoList = listOf(
            mockedDto,
            mockedDto.copy(
                name = "Mocked2 Sake Shop",
                rating = 4.8f
            )
        )

        // When
        val result = dtoList.toDomain()

        // Then
        assertEquals(2, result.size)
        assertEquals("Mocked Sake Shop", result[0].name)
        assertEquals("Mocked2 Sake Shop", result[1].name)
        assertEquals(4.5f, result[0].rating, 0.01f)
        assertEquals(4.8f, result[1].rating, 0.01f)
    }

    @Test
    fun `toDomain should convert empty list correctly`() {
        // Given
        val emptyList = emptyList<SakeShopDto>()

        // When
        val result = emptyList.toDomain()

        // Then
        assertTrue(result.isEmpty())
        assertEquals(0, result.size)
    }
}
