package com.sakestores.data.datasource

import com.google.gson.Gson
import android.content.Context
import com.google.gson.reflect.TypeToken
import com.sakestores.data.dto.SakeShopDto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Data source responsible for loading sake shop data from local assets.
 *
 * This class uses Gson to parse JSON data stored in the app's assets folder.
 * It provides suspend functions to asynchronously fetch the list of sake shops
 * or retrieve a specific sake shop by its name.
 *
 * @property context The Android application context injected via Hilt.
 * @property gson Gson instance for JSON parsing.
 */
class SakeShopDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {

    /**
     * Loads the list of sake shops from the local JSON asset file asynchronously.
     *
     * @return A [Result] wrapping a list of [SakeShopDto] on success, or an exception on failure.
     */
    suspend fun getSakeShops(): Result<List<SakeShopDto>> = withContext(Dispatchers.IO) {
        try {
            val json = context.assets.open("sake_shops.json").bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<SakeShopDto>>() {}.type
            val sakeShops = gson.fromJson<List<SakeShopDto>>(json, listType)
            Result.success(sakeShops)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Retrieves a sake shop by its exact name asynchronously.
     *
     * This function fetches all sake shops and then finds the one matching the given name.
     *
     * @param name The exact name of the sake shop to find.
     * @return A [Result] wrapping the found [SakeShopDto] or null if not found, or an exception on failure.
     */
    suspend fun getSakeShopByName(name: String): Result<SakeShopDto?> {
        return getSakeShops().mapCatching { shops ->
            shops.find { it.name == name }
        }
    }
}
