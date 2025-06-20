package com.sakestores.android.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakestores.domain.usecase.GetSakeShopDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SakeShopDetailsViewModel @Inject constructor(
    private val getSakeShopDetailsUseCase: GetSakeShopDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SakeShopDetailsUiState())
    val uiState: StateFlow<SakeShopDetailsUiState> = _uiState.asStateFlow()

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