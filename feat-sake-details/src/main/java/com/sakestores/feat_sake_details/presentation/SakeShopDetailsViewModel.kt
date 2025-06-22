package com.sakestores.feat_sake_details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakestores.domain.usecase.GetSakeShopDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the UI state of the Sake Shop Details screen.
 *
 * @property getSakeShopDetailsUseCase Use case to fetch the details of a sake shop by name.
 */
@HiltViewModel
class SakeShopDetailsViewModel @Inject constructor(
    private val getSakeShopDetailsUseCase: GetSakeShopDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SakeShopDetailsUiState())
    /**
     * Exposes the UI state as a read-only [StateFlow].
     */
    val uiState: StateFlow<SakeShopDetailsUiState> = _uiState.asStateFlow()

    /**
     * Loads the details of a sake shop given its [shopName].
     *
     * Updates the UI state to loading before fetching data.
     * On success, updates the state with the fetched sake shop details.
     * On failure, updates the state with an error message.
     *
     * @param shopName The name of the sake shop to load details for.
     */
    fun loadSakeShopDetails(shopName: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            getSakeShopDetailsUseCase(shopName)
                .onSuccess { sakeShop ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        sakeShop = sakeShop
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Unknown error occurred"
                    )
                }
        }
    }
}
