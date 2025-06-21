package com.sakestores.data.datasource

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import com.sakestores.data.dto.SakeShopDto
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import java.io.ByteArrayInputStream
import java.io.IOException

class SakeShopDataSourceTest {

    // Mocks
    private lateinit var context: Context
    private lateinit var assetManager: AssetManager
    private lateinit var gson: Gson
    private lateinit var dataSource: SakeShopDataSource

    // Test data
    private val validJson = """
        [
            {
                "name": "Test Sake Shop",
                "description": "A great sake shop",
                "picture": "https://example.com/test.jpg",
                "rating": 4.5,
                "address": "123 Test Street",
                "coordinates": [35.6762, 139.6503],
                "google_maps_link": "https://maps.google.com/test",
                "website": "https://test-sake-shop.com"
            }
        ]
    """.trimIndent()

    @Before
    fun setup() {
        context = mockk()
        assetManager = mockk()
        gson = Gson() // Usar Gson real para parsing

        every { context.assets } returns assetManager

        dataSource = SakeShopDataSource(context, gson)
    }

    @Test
    fun `getSakeShops should return success when JSON is valid`() = runTest {
        // Given
        val inputStream = ByteArrayInputStream(validJson.toByteArray())
        every { assetManager.open("sake_shops.json") } returns inputStream

        // When
        val result = dataSource.getSakeShops()

        // Then
        assertTrue(result.isSuccess)
        val sakeShops = result.getOrNull()
        assertNotNull(sakeShops)
        assertEquals(1, sakeShops!!.size)
        assertEquals("Test Sake Shop", sakeShops[0].name)
        assertEquals(4.5f, sakeShops[0].rating, 0.01f)
        assertEquals("A great sake shop", sakeShops[0].description)
    }

    @Test
    fun `getSakeShops should return failure when file not found`() = runTest {
        // Given
        every { assetManager.open("sake_shops.json") } throws IOException("File not found")

        // When
        val result = dataSource.getSakeShops()

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IOException)
        assertEquals("File not found", result.exceptionOrNull()?.message)
    }

    @Test
    fun `getSakeShops should return failure when JSON is malformed`() = runTest {
        // Given
        val malformedJson = """
        [
            {
                "name": "Test Shop",
                "invalid_json": 
            }
        ]
    """.trimIndent()

        val inputStream = ByteArrayInputStream(malformedJson.toByteArray())
        every { assetManager.open("sake_shops.json") } returns inputStream

        // When
        val result = dataSource.getSakeShops()

        // Then
        assertTrue(result.isFailure)
        assertNotNull(result.exceptionOrNull())
    }

    @Test
    fun `getSakeShopByName should return success when shop is found`() = runTest {
        // Given
        val inputStream = ByteArrayInputStream(validJson.toByteArray())
        every { assetManager.open("sake_shops.json") } returns inputStream

        // When
        val result = dataSource.getSakeShopByName("Test Sake Shop")

        // Then
        assertTrue(result.isSuccess)
        val sakeShop = result.getOrNull()
        assertNotNull(sakeShop)
        assertEquals("Test Sake Shop", sakeShop!!.name)
        assertEquals(4.5f, sakeShop.rating, 0.01f)
    }

    @Test
    fun `getSakeShopByName should return success with null when shop not found`() = runTest {
        // Given
        val inputStream = ByteArrayInputStream(validJson.toByteArray())
        every { assetManager.open("sake_shops.json") } returns inputStream

        // When
        val result = dataSource.getSakeShopByName("Non-existent Shop")

        // Then
        assertTrue(result.isSuccess)
        val sakeShop = result.getOrNull()
        assertNull(sakeShop)
    }
}