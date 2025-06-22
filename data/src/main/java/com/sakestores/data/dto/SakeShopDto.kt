package com.sakestores.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object representing a sake shop.
 *
 * @property name The name of the sake shop.
 * @property description A brief description of the sake shop.
 * @property picture Optional URL or path to the picture of the sake shop.
 * @property rating The rating of the sake shop, typically on a scale (e.g., 0 to 5).
 * @property address The physical address of the sake shop.
 * @property coordinates The geographical coordinates of the shop as a list of two Doubles: latitude and longitude.
 * @property googleMapsLink A URL linking to the shop location on Google Maps.
 * @property website The official website URL of the sake shop.
 */
@Serializable
data class SakeShopDto(
    val name: String,
    val description: String,
    val picture: String?,
    val rating: Float,
    val address: String,
    val coordinates: List<Double>,
    @SerializedName("google_maps_link")
    val googleMapsLink: String,
    val website: String
)
