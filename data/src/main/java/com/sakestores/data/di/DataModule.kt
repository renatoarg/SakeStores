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

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindSakeShopRepository(
        sakeShopRepositoryImpl: SakeShopRepositoryImpl
    ): SakeShopRepository

    companion object {
        @Provides
        @Singleton
        fun providesGson(): Gson = Gson()
    }
}