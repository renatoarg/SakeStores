package com.sakestores.domain.usecase

import com.sakestores.domain.model.SakeShop
import com.sakestores.domain.repository.SakeShopRepository
import javax.inject.Inject

/**
 * Use case to fetch a list of all available [SakeShop]s.
 *
 * @property repository The [SakeShopRepository] used to retrieve the list.
 */
class GetSakeShopsUseCase @Inject constructor(
    private val repository: SakeShopRepository
) {
    /**
     * Invokes the use case to get the list of sake shops.
     *
     * @return A [Result] wrapping a list of [SakeShop] on success or an error on failure.
     */
    suspend operator fun invoke(): Result<List<SakeShop>> {
        return repository.getSakeShops()
    }
}
