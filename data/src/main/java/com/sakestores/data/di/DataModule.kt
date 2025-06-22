package com.sakestores.data.di

import com.google.gson.Gson
import com.sakestores.data.repository.SakeShopRepositoryImpl
import com.sakestores.domain.repository.SakeShopRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module responsible for providing data layer dependencies.
 *
 * This module binds the implementation of [SakeShopRepository] to its interface
 * and provides a singleton instance of [Gson] for JSON parsing.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    /**
     * Binds the [SakeShopRepositoryImpl] implementation to the [SakeShopRepository] interface.
     *
     * @param sakeShopRepositoryImpl The concrete implementation of [SakeShopRepository].
     * @return The bound [SakeShopRepository].
     */
    @Binds
    @Singleton
    abstract fun bindSakeShopRepository(
        sakeShopRepositoryImpl: SakeShopRepositoryImpl
    ): SakeShopRepository

    companion object {
        /**
         * Provides a singleton instance of [Gson].
         *
         * @return A new [Gson] instance.
         */
        @Provides
        @Singleton
        fun providesGson(): Gson = Gson()
    }
}
