package com.sakestores.feat_sake_details.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sakestores.domain.model.SakeShop
import com.sakestores.domain.usecase.GetSakeShopDetailsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for [SakeShopDetailsViewModel].
 *
 * This test class verifies the initial state, successful data loading,
 * handling of null results, error scenarios, and error clearing behavior
 * of the ViewModel when fetching sake shop details.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SakeShopDetailsViewModelTest {

    /**
     * Rule to execute LiveData tasks synchronously.
     */
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var getSakeShopDetailsUseCase: GetSakeShopDetailsUseCase
    private lateinit var viewModel: SakeShopDetailsViewModel

    private val testShopName = "Test Sake Shop"
    private val sampleSakeShop = SakeShop(
        name = "Test Sake Shop",
        description = "A test shop",
        picture = "https://example.com/test.jpg",
        rating = 4.5f,
        address = "123 Test St",
        coordinates = listOf(35.6762, 139.6503),
        googleMapsLink = "https://maps.google.com/test",
        website = "https://test.com"
    )

    /**
     * Sets up the test environment, including main dispatcher and mocks.
     */
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getSakeShopDetailsUseCase = mockk()
        viewModel = SakeShopDetailsViewModel(getSakeShopDetailsUseCase)
    }

    /**
     * Resets main dispatcher after tests.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Tests that the initial UI state is empty with no loading, data or error.
     */
    @Test
    fun `initial state should be empty`() {
        // When

        // Then
        val initialState = viewModel.uiState.value
        assertFalse(initialState.isLoading)
        assertNull(initialState.sakeShop)
        assertNull(initialState.errorMessage)

        // Verify
        coVerify(exactly = 0) { getSakeShopDetailsUseCase(any()) }
    }

    /**
     * Tests that loading sake shop details successfully updates the UI state.
     */
    @Test
    fun `loadSakeShopDetails should load shop successfully when found`() = runTest {
        // Given
        coEvery { getSakeShopDetailsUseCase(testShopName) } returns Result.success(sampleSakeShop)

        // When
        viewModel.loadSakeShopDetails(testShopName)

        // Then
        val finalState = viewModel.uiState.value
        assertFalse(finalState.isLoading)
        assertNotNull(finalState.sakeShop)
        assertEquals("Test Sake Shop", finalState.sakeShop?.name)
        assertEquals(4.5f, finalState.sakeShop?.rating ?: 0f, 0.01f)
        assertNull(finalState.errorMessage)

        // Verify use case was called with correct parameter
        coVerify(exactly = 1) { getSakeShopDetailsUseCase(testShopName) }
    }

    /**
     * Tests that loading sake shop details returning null results
     * leaves UI state without data or error.
     */
    @Test
    fun `loadSakeShopDetails should handle shop not found correctly`() = runTest {
        // Given
        coEvery { getSakeShopDetailsUseCase(testShopName) } returns Result.success(null)

        // When
        viewModel.loadSakeShopDetails(testShopName)

        // Then
        val finalState = viewModel.uiState.value
        assertFalse(finalState.isLoading)
        assertNull(finalState.sakeShop)
        assertNull(finalState.errorMessage)

        // Verify use case was called
        coVerify(exactly = 1) { getSakeShopDetailsUseCase(testShopName) }
    }

    /**
     * Tests that errors returned by the use case update the UI state with the error message.
     */
    @Test
    fun `loadSakeShopDetails should handle error correctly`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { getSakeShopDetailsUseCase(testShopName) } returns Result.failure(Exception(errorMessage))

        // When
        viewModel.loadSakeShopDetails(testShopName)

        // Then
        val finalState = viewModel.uiState.value
        assertFalse(finalState.isLoading)
        assertNull(finalState.sakeShop)
        assertEquals(errorMessage, finalState.errorMessage)

        // Verify use case was called
        coVerify(exactly = 1) { getSakeShopDetailsUseCase(testShopName) }
    }

    /**
     * Tests that errors without message set a default "Unknown error occurred".
     */
    @Test
    fun `loadSakeShopDetails should handle exception without message`() = runTest {
        // Given
        val exceptionWithoutMessage = RuntimeException() // sem message
        coEvery { getSakeShopDetailsUseCase(testShopName) } returns Result.failure(exceptionWithoutMessage)

        // When
        viewModel.loadSakeShopDetails(testShopName)

        // Then
        val finalState = viewModel.uiState.value
        assertFalse(finalState.isLoading)
        assertNull(finalState.sakeShop)
        assertEquals("Unknown error occurred", finalState.errorMessage)
    }

    /**
     * Tests that a new successful load clears any previous error messages.
     */
    @Test
    fun `loadSakeShopDetails should clear previous error on new successful call`() = runTest {
        // Given
        coEvery { getSakeShopDetailsUseCase(testShopName) } returns Result.failure(Exception("Network error"))
        viewModel.loadSakeShopDetails(testShopName)

        // Verify initial error state
        assertEquals("Network error", viewModel.uiState.value.errorMessage)

        // When
        coEvery { getSakeShopDetailsUseCase(testShopName) } returns Result.success(sampleSakeShop)
        viewModel.loadSakeShopDetails(testShopName)

        // Then
        val finalState = viewModel.uiState.value
        assertFalse(finalState.isLoading)
        assertNotNull(finalState.sakeShop)
        assertEquals("Test Sake Shop", finalState.sakeShop?.name)
        assertNull(finalState.errorMessage)
    }
}
