package com.sakestores.domain.usecase

import com.sakestores.domain.model.SakeShop
import com.sakestores.domain.repository.SakeShopRepository
import javax.inject.Inject

class GetSakeShopsUseCase @Inject constructor(
    private val repository: SakeShopRepository
) {
    suspend operator fun invoke(): Result<List<SakeShop>> {
        return repository.getSakeShops()
    }
}