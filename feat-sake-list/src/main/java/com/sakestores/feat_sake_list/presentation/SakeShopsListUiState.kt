package com.sakestores.feat_sake_list.presentation

import com.sakestores.domain.model.SakeShop

data class SakeShopsListUiState(
    val isLoading: Boolean = false,
    val sakeShops: List<SakeShop> = emptyList(),
    val errorMessage: String? = null
)