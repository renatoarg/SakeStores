package com.sakestores.data.repository

import com.sakestores.data.datasource.SakeShopDataSource
import com.sakestores.data.mapper.toDomain
import com.sakestores.domain.model.SakeShop
import com.sakestores.domain.repository.SakeShopRepository
import javax.inject.Inject

class SakeShopRepositoryImpl @Inject constructor(
    private val dataSource: SakeShopDataSource
): SakeShopRepository {
    override suspend fun getSakeShops(): Result<List<SakeShop>> {
        return dataSource.getSakeShops().mapCatching { dtos ->
            dtos.toDomain()
        }
    }

    override suspend fun getSakeShopByName(name: String): Result<SakeShop?> {
        return dataSource.getSakeShopByName(name).mapCatching { dto ->
            dto?.toDomain()
        }
    }
}