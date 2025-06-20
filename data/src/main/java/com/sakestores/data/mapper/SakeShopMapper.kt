package com.sakestores.data.mapper

import com.sakestores.data.dto.SakeShopDto
import com.sakestores.domain.model.SakeShop

fun SakeShopDto.toDomain(): SakeShop {
    return SakeShop(
        name = name,
        description = description,
        picture = picture,
        rating = rating,
        address = address,
        coordinates = coordinates,
        googleMapsLink = googleMapsLink,
        website = website
    )
}

fun List<SakeShopDto>.toDomain(): List<SakeShop> {
    return map { it.toDomain()}
}
