package com.sakestores.data.repository

import com.sakestores.data.datasource.SakeShopDataSource
import com.sakestores.data.mapper.toDomain
import com.sakestores.domain.model.SakeShop
import com.sakestores.domain.repository.SakeShopRepository
import javax.inject.Inject

/**
 * Implementation of [SakeShopRepository] that fetches data from [SakeShopDataSource]
 * and maps the data transfer objects to domain models.
 *
 * @property dataSource The data source providing sake shop data.
 */
class SakeShopRepositoryImpl @Inject constructor(
    private val dataSource: SakeShopDataSource
): SakeShopRepository {

    /**
     * Retrieves a list of sake shops from the data source and maps them to domain models.
     *
     * @return Result wrapping a list of [SakeShop] on success, or an exception on failure.
     */
    override suspend fun getSakeShops(): Result<List<SakeShop>> {
        return dataSource.getSakeShops().mapCatching { dtos ->
            dtos.toDomain()
        }
    }

    /**
     * Retrieves a single sake shop by its name from the data source and maps it to a domain model.
     *
     * @param name The name of the sake shop to retrieve.
     * @return Result wrapping the [SakeShop] if found, null if not found, or an exception on failure.
     */
    override suspend fun getSakeShopByName(name: String): Result<SakeShop?> {
        return dataSource.getSakeShopByName(name).mapCatching { dto ->
            dto?.toDomain()
        }
    }
}
