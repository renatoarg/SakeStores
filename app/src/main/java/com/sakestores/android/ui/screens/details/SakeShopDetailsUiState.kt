package com.sakestores.android.ui.screens.details

import com.sakestores.domain.model.SakeShop

data class SakeShopDetailsUiState(
    val isLoading: Boolean = false,
    val sakeShop: SakeShop? = null,
    val errorMessage: String? = null
)