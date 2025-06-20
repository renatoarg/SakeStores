package com.sakestores.domain.repository

import com.sakestores.domain.model.SakeShop

interface SakeShopRepository {
    suspend fun getSakeShops(): Result<List<SakeShop>>
    suspend fun getSakeShopByName(name: String): Result<SakeShop?>
}