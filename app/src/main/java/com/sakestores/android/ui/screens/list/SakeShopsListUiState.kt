package com.sakestores.android.ui.screens.list

import com.sakestores.domain.model.SakeShop

data class SakeShopsListUiState(
    val isLoading: Boolean = false,
    val sakeShops: List<SakeShop> = emptyList(),
    val errorMessage: String? = null
)