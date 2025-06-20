package com.sakestores.android.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakestores.domain.usecase.GetSakeShopsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SakeShopsListViewModel @Inject constructor(
    private val getSakeShopsUseCase: GetSakeShopsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SakeShopsListUiState())
    val uiState: StateFlow<SakeShopsListUiState> = _uiState.asStateFlow()

    init {
        loadSakeShops()
    }

    fun loadSakeShops() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            getSakeShopsUseCase()
                .onSuccess { sakeShops ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        sakeShops = sakeShops
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