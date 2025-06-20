package com.sakestores.feat_sake_details.presentation

import com.sakestores.domain.model.SakeShop

data class SakeShopDetailsUiState(
    val isLoading: Boolean = false,
    val sakeShop: SakeShop? = null,
    val errorMessage: String? = null
)