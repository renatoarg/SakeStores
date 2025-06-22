package com.sakestores.domain.model

/**
 * Data class representing a SakeShop with its details.
 *
 * @property name The name of the sake shop.
 * @property description A brief description of the sake shop.
 * @property picture Optional URL or path to a picture representing the shop.
 * @property rating The average rating of the shop (e.g., from 0.0 to 5.0).
 * @property address The physical address of the sake shop.
 * @property coordinates A list containing the latitude and longitude of the shop.
 * @property googleMapsLink A URL linking to the location in Google Maps.
 * @property website The website URL of the sake shop.
 */
data class SakeShop(
    val name: String,
    val description: String,
    val picture: String?,
    val rating: Float,
    val address: String,
    val coordinates: List<Double>,
    val googleMapsLink: String,
    val website: String
)
