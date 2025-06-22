package com.sakestores.feat_sake_list.presentation

import com.sakestores.domain.model.SakeShop

/**
 * Represents the UI state for the sake shops list screen.
 *
 * @property isLoading Indicates whether the data is currently being loaded.
 * @property sakeShops The list of sake shops to display.
 * @property errorMessage Optional error message to show in the UI if loading fails.
 */
data class SakeShopsListUiState(
    val isLoading: Boolean = false,
    val sakeShops: List<SakeShop> = emptyList(),
    val errorMessage: String? = null
)
