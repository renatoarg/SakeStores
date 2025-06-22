package com.sakestores.domain.usecase

import com.sakestores.domain.model.SakeShop
import com.sakestores.domain.repository.SakeShopRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [GetSakeShopsUseCase].
 *
 * Tests the behavior of the use case when fetching the list of sake shops,
 * covering success, failure, and empty list scenarios.
 */
class GetSakeShopsUseCaseTest {

    private lateinit var repository: SakeShopRepository
    private lateinit var useCase: GetSakeShopsUseCase

    /**
     * Sets up the mock repository and use case before each test.
     */
    @Before
    fun setup() {
        repository = mockk()
        useCase = GetSakeShopsUseCase(repository)
    }

    /**
     * Tests that invoking the use case returns a successful result
     * with the list of sake shops when the repository returns success.
     */
    @Test
    fun `invoke should return success when repository returns success`() = runTest {
        // Given
        val expectedShops = listOf(
            SakeShop(
                name = "Test Shopsss 1",
                description = "Test Description 1",
                picture = "test_url_1",
                rating = 4.0f,
                address = "Test Address 1",
                coordinates = listOf(36.0, 138.0),
                googleMapsLink = "test_maps_link_1",
                website = "test_website_1"
            ),
            SakeShop(
                name = "Test Shop 2",
                description = "Test Description 2",
                picture = "test_url_2",
                rating = 4.5f,
                address = "Test Address 2",
                coordinates = listOf(37.0, 139.0),
                googleMapsLink = "test_maps_link_2",
                website = "test_website_2"
            )
        )
        coEvery { repository.getSakeShops() } returns Result.success(expectedShops)

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(expectedShops, result.getOrNull())
        coVerify(exactly = 1) { repository.getSakeShops() }
    }

    /**
     * Tests that invoking the use case returns a failure result
     * when the repository returns a failure.
     */
    @Test
    fun `invoke should return failure when repository returns failure`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { repository.getSakeShops() } returns Result.failure(exception)

        // When
        val result = useCase()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 1) { repository.getSakeShops() }
    }

    /**
     * Tests that invoking the use case returns a successful result with
     * an empty list when the repository returns an empty list.
     */
    @Test
    fun `invoke should return empty list when repository returns empty list`() = runTest {
        // Given
        val emptyList = emptyList<SakeShop>()
        coEvery { repository.getSakeShops() } returns Result.success(emptyList)

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(emptyList, result.getOrNull())
        coVerify(exactly = 1) { repository.getSakeShops() }
    }
}
