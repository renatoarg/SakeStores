package com.sakestores.data.datasource

import com.google.gson.Gson
import android.content.Context
import com.google.gson.reflect.TypeToken
import com.sakestores.data.dto.SakeShopDto
import com.sakestores.domain.model.SakeShop
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SakeShopDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {

    suspend fun getSakeShops(): Result<List<SakeShopDto>> {
        return try {
            val json = context.assets.open("sake_shops.json").bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<SakeShopDto>>() {}.type
            val sakeShops = gson.fromJson<List<SakeShopDto>>(json, listType)
            Result.success(sakeShops)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSakeShopByName(name: String): Result<SakeShopDto?> {
        return getSakeShops().mapCatching { shops ->
            shops.find { it.name == name }
        }
    }
}