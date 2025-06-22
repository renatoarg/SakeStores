package com.sakestores.feat_sake_details.presentation

import com.sakestores.domain.model.SakeShop

/**
 * Represents the UI state for the sake shop details screen.
 *
 * @property isLoading Indicates whether the details are currently loading.
 * @property sakeShop Holds the details of the selected sake shop, or null if not loaded.
 * @property errorMessage Contains an error message if loading failed, or null otherwise.
 */
data class SakeShopDetailsUiState(
    val isLoading: Boolean = false,
    val sakeShop: SakeShop? = null,
    val errorMessage: String? = null
)
