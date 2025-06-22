package com.sakestores.feat_sake_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sakestores.domain.usecase.GetSakeShopsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the UI state of the sake shops list screen.
 *
 * It loads the list of sake shops using [GetSakeShopsUseCase] and exposes the UI state via
 * a [StateFlow] of [SakeShopsListUiState].
 *
 * @property getSakeShopsUseCase Use case to fetch the list of sake shops.
 */
@HiltViewModel
class SakeShopsListViewModel @Inject constructor(
    private val getSakeShopsUseCase: GetSakeShopsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SakeShopsListUiState())
    /**
     * The UI state of the sake shops list screen, exposed as a read-only StateFlow.
     */
    val uiState: StateFlow<SakeShopsListUiState> = _uiState.asStateFlow()

    init {
        loadSakeShops()
    }

    /**
     * Loads the list of sake shops asynchronously.
     *
     * Updates the [uiState] to show loading state, success with data,
     * or failure with an error message.
     */
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
