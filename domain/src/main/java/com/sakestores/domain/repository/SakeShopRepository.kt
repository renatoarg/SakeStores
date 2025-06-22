package com.sakestores.domain.repository

import com.sakestores.domain.model.SakeShop

/**
 * Interface defining the repository for accessing SakeShop data.
 */
interface SakeShopRepository {

    /**
     * Retrieves a list of all available SakeShops.
     *
     * @return A [Result] wrapping a list of [SakeShop] objects on success, or an error on failure.
     */
    suspend fun getSakeShops(): Result<List<SakeShop>>

    /**
     * Retrieves a specific [SakeShop] by its name.
     *
     * @param name The name of the sake shop to retrieve.
     * @return A [Result] wrapping the [SakeShop] if found, or null if not found, or an error on failure.
     */
    suspend fun getSakeShopByName(name: String): Result<SakeShop?>
}
