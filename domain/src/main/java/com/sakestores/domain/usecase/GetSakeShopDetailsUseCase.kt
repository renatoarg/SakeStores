package com.sakestores.domain.usecase

import com.sakestores.domain.model.SakeShop
import com.sakestores.domain.repository.SakeShopRepository
import javax.inject.Inject

/**
 * Use case to fetch details of a specific [SakeShop] by its name.
 *
 * @property repository The [SakeShopRepository] used to fetch the data.
 */
class GetSakeShopDetailsUseCase @Inject constructor(
    private val repository: SakeShopRepository
) {
    /**
     * Invokes the use case to get the details of a sake shop.
     *
     * @param shopName The name of the sake shop to retrieve.
     * @return A [Result] wrapping the [SakeShop] if found, or null if not found, or an error on failure.
     */
    suspend operator fun invoke(shopName: String): Result<SakeShop?> {
        return repository.getSakeShopByName(shopName)
    }
}
