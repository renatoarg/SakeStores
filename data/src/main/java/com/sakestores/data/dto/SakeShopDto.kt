package com.sakestores.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

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