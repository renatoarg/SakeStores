package com.sakestores.feat_sake_list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sakestores.domain.model.SakeShop
import com.sakestores.domain.usecase.GetSakeShopsUseCase
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
 * Unit tests for [SakeShopsListViewModel], verifying its interaction with [GetSakeShopsUseCase]
 * and the emitted UI state in various scenarios.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SakeShopsListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    // Mocks
    private lateinit var getSakeShopsUseCase: GetSakeShopsUseCase
    private lateinit var viewModel: SakeShopsListViewModel

    // Test data
    private val sampleSakeShops = listOf(
        SakeShop(
            name = "Test Sake Shop",
            description = "A test shop",
            picture = "https://example.com/test.jpg",
            rating = 4.5f,
            address = "123 Test St",
            coordinates = listOf(35.6762, 139.6503),
            googleMapsLink = "https://maps.google.com/test",
            website = "https://test.com"
        )
    )

    /**
     * Sets up the test dispatcher and mocks before each test.
     */
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getSakeShopsUseCase = mockk()
    }

    /**
     * Resets the main dispatcher after each test.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Verifies that the ViewModel correctly loads sake shops on initialization
     * and emits a UI state with the expected data and no errors.
     */
    @Test
    fun `init should load sake shops successfully`() = runTest {
        // Given
        coEvery { getSakeShopsUseCase() } returns Result.success(sampleSakeShops)

        // When
        viewModel = SakeShopsListViewModel(getSakeShopsUseCase)

        // Then
        val finalState = viewModel.uiState.value
        assertFalse(finalState.isLoading)
        assertEquals(1, finalState.sakeShops.size)
        assertEquals("Test Sake Shop", finalState.sakeShops[0].name)
        assertNull(finalState.errorMessage)

        // Verify use case was called
        coVerify(exactly = 1) { getSakeShopsUseCase() }
    }

    /**
     * Verifies that the ViewModel correctly handles an error from the use case
     * and emits a UI state with an error message.
     */
    @Test
    fun `init should handle error correctly`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { getSakeShopsUseCase() } returns Result.failure(Exception(errorMessage))

        // When
        viewModel = SakeShopsListViewModel(getSakeShopsUseCase)

        // Then
        val finalState = viewModel.uiState.value
        assertFalse(finalState.isLoading)
        assertTrue(finalState.sakeShops.isEmpty())
        assertEquals(errorMessage, finalState.errorMessage)

        // Verify use case was called
        coVerify(exactly = 1) { getSakeShopsUseCase() }
    }

    /**
     * Verifies that calling [SakeShopsListViewModel.loadSakeShops] emits loading states
     * and updates the UI state accordingly.
     */
    @Test
    fun `loadSakeShops should emit correct loading states`() = runTest {
        // Given
        coEvery { getSakeShopsUseCase() } returns Result.success(sampleSakeShops)
        viewModel = SakeShopsListViewModel(getSakeShopsUseCase)

        // Reset mock
        coEvery { getSakeShopsUseCase() } returns Result.success(emptyList())

        // When
        viewModel.loadSakeShops()

        // Then
        val finalState = viewModel.uiState.value
        assertFalse(finalState.isLoading)
        assertTrue(finalState.sakeShops.isEmpty())
        assertNull(finalState.errorMessage)

        // Verify use case was called twice (init + manual)
        coVerify(exactly = 2) { getSakeShopsUseCase() }
    }

    /**
     * Verifies that the ViewModel gracefully handles an exception without a message
     * and emits a default error message in the UI state.
     */
    @Test
    fun `loadSakeShops should handle exception without message`() = runTest {
        // Given
        val exceptionWithoutMessage = RuntimeException()
        coEvery { getSakeShopsUseCase() } returns Result.failure(exceptionWithoutMessage)

        // When
        viewModel = SakeShopsListViewModel(getSakeShopsUseCase)

        // Then
        val finalState = viewModel.uiState.value
        assertFalse(finalState.isLoading)
        assertTrue(finalState.sakeShops.isEmpty())
        assertEquals("Unknown error occurred", finalState.errorMessage)
    }

    /**
     * Verifies that calling [SakeShopsListViewModel.loadSakeShops] after an error
     * clears the previous error message if the new result is successful.
     */
    @Test
    fun `loadSakeShops should clear previous error message on new call`() = runTest {
        // Given
        coEvery { getSakeShopsUseCase() } returns Result.failure(Exception("First error"))
        viewModel = SakeShopsListViewModel(getSakeShopsUseCase)

        // Verify initial error state
        assertEquals("First error", viewModel.uiState.value.errorMessage)

        // When
        coEvery { getSakeShopsUseCase() } returns Result.success(sampleSakeShops)
        viewModel.loadSakeShops()

        // Then
        val finalState = viewModel.uiState.value
        assertFalse(finalState.isLoading)
        assertEquals(1, finalState.sakeShops.size)
        assertNull(finalState.errorMessage)
    }
}
