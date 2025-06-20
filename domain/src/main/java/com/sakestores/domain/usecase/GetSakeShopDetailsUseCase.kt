package com.sakestores.domain.usecase

import com.sakestores.domain.model.SakeShop
import com.sakestores.domain.repository.SakeShopRepository
import javax.inject.Inject

class GetSakeShopDetailsUseCase @Inject constructor(
    private val repository: SakeShopRepository
) {
    suspend operator fun invoke(shopName: String): Result<SakeShop?> {
        return repository.getSakeShopByName(shopName)
    }
}