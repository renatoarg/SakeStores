package com.sakestores.data.mapper

import com.sakestores.data.dto.SakeShopDto
import com.sakestores.domain.model.SakeShop

/**
 * Extension function to map a [SakeShopDto] to its domain model [SakeShop].
 *
 * @receiver SakeShopDto The data transfer object to be converted.
 * @return SakeShop The corresponding domain model object.
 */
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

/**
 * Extension function to map a list of [SakeShopDto] objects to a list of domain model [SakeShop] objects.
 *
 * @receiver List<SakeShopDto> The list of DTOs to be converted.
 * @return List<SakeShop> The list of corresponding domain model objects.
 */
fun List<SakeShopDto>.toDomain(): List<SakeShop> {
    return map { it.toDomain() }
}
