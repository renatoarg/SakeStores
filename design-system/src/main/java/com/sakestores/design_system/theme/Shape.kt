package com.sakestores.design_system.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val SakeShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

// Shapes customizados para casos específicos
object CustomShapes {
    val card = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = 8.dp,
        bottomEnd = 8.dp
    )

    val button = RoundedCornerShape(12.dp)
    val chip = RoundedCornerShape(50) // Totalmente arredondado
    val dialog = RoundedCornerShape(24.dp)
}