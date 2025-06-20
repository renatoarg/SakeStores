package com.sakestores.domain.usecase

import com.sakestores.domain.model.SakeShop
import com.sakestores.domain.repository.SakeShopRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetSakeShopDetailsUseCaseTest {

    private lateinit var repository: SakeShopRepository
    private lateinit var useCase: GetSakeShopDetailsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetSakeShopDetailsUseCase(repository)
    }

    @Test
    fun `invoke should return success with shop when repository finds shop`() = runTest {
        // Given
        val shopName = "Test Shop"
        val expectedShop = SakeShop(
            name = shopName,
            description = "Test Description",
            picture = "test_url",
            rating = 4.0f,
            address = "Test Address",
            coordinates = listOf(36.0, 138.0),
            googleMapsLink = "test_maps_link",
            website = "test_website"
        )
        coEvery { repository.getSakeShopByName(shopName) } returns Result.success(expectedShop)

        // When
        val result = useCase(shopName)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedShop, result.getOrNull())
        coVerify(exactly = 1) { repository.getSakeShopByName(shopName) }
    }

    @Test
    fun `invoke should return success with null when repository doesn't find shop`() = runTest {
        // Given
        val shopName = "Non-existent Shop"
        coEvery { repository.getSakeShopByName(shopName) } returns Result.success(null)

        // When
        val result = useCase(shopName)

        // Then
        assertTrue(result.isSuccess)
        assertNull(result.getOrNull())
        coVerify(exactly = 1) { repository.getSakeShopByName(shopName) }
    }

    @Test
    fun `invoke should return failure when repository returns failure`() = runTest {
        // Given
        val shopName = "Test Shop"
        val exception = Exception("Database error")
        coEvery { repository.getSakeShopByName(shopName) } returns Result.failure(exception)

        // When
        val result = useCase(shopName)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { repository.getSakeShopByName(shopName) }
    }
}