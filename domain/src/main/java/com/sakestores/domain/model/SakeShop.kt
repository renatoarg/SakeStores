package com.sakestores.domain.model

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