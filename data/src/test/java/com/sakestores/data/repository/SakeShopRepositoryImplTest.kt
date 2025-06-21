package com.sakestores.data.repository

import com.sakestores.data.datasource.SakeShopDataSource
import com.sakestores.data.dto.SakeShopDto
import com.sakestores.domain.model.SakeShop
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class SakeShopRepositoryImplTest {

    // Mocks
    private lateinit var dataSource: SakeShopDataSource
    private lateinit var repository: SakeShopRepositoryImpl

    // Test data
    private val sampleDto = SakeShopDto(
        name = "Test Sake Shop",
        description = "A great sake shop for testing",
        picture = "https://example.com/test.jpg",
        rating = 4.5f,
        address = "123 Test Street",
        coordinates = listOf(35.6762, 139.6503),
        googleMapsLink = "https://maps.google.com/test",
        website = "https://test-sake-shop.com"
    )

    @Before
    fun setup() {
        dataSource = mockk()
        repository = SakeShopRepositoryImpl(dataSource)
    }

    @Test
    fun `getSakeShops should return success when dataSource returns success`() = runTest {
        // Given
        val dtoList = listOf(sampleDto, sampleDto.copy(name = "Another Shop"))
        coEvery { dataSource.getSakeShops() } returns Result.success(dtoList)

        // When
        val result = repository.getSakeShops()

        // Then
        assertTrue(result.isSuccess)
        val sakeShops = result.getOrNull()
        assertNotNull(sakeShops)
        assertEquals(2, sakeShops!!.size)
        assertEquals("Test Sake Shop", sakeShops[0].name)
        assertEquals("Another Shop", sakeShops[1].name)
        assertEquals(4.5f, sakeShops[0].rating, 0.01f)
    }

    @Test
    fun `getSakeShops should return failure when dataSource returns failure`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { dataSource.getSakeShops() } returns Result.failure(exception)

        // When
        val result = repository.getSakeShops()

        // Then
        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getSakeShopByName should return success when shop is found`() = runTest {
        // Given
        val shopName = "Test Sake Shop"
        coEvery { dataSource.getSakeShopByName(shopName) } returns Result.success(sampleDto)

        // When
        val result = repository.getSakeShopByName(shopName)

        // Then
        assertTrue(result.isSuccess)
        val sakeShop = result.getOrNull()
        assertNotNull(sakeShop)
        assertEquals("Test Sake Shop", sakeShop!!.name)
        assertEquals(4.5f, sakeShop.rating, 0.01f)
        assertEquals("123 Test Street", sakeShop.address)
    }

    @Test
    fun `getSakeShopByName should return success with null when shop is not found`() = runTest {
        // Given
        val shopName = "Non-existent Shop"
        coEvery { dataSource.getSakeShopByName(shopName) } returns Result.success(null)

        // When
        val result = repository.getSakeShopByName(shopName)

        // Then
        assertTrue(result.isSuccess)
        val sakeShop = result.getOrNull()
        assertNull(sakeShop)
    }

    @Test
    fun `getSakeShopByName should return failure when dataSource returns failure`() = runTest {
        // Given
        val shopName = "Test Sake Shop"
        val exception = Exception("Database error")
        coEvery { dataSource.getSakeShopByName(shopName) } returns Result.failure(exception)

        // When
        val result = repository.getSakeShopByName(shopName)

        // Then
        assertTrue(result.isFailure)
        assertEquals("Database error", result.exceptionOrNull()?.message)
    }
}