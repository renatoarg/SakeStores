package com.sakestores.design_system.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Shape definitions for the Sake Stores design system.
 *
 * This object defines the standard corner shapes used across the UI components,
 * with varying corner radii from extraSmall to extraLarge.
 */
val SakeShapes = Shapes(
    /** Extra small rounded corners with 4.dp radius */
    extraSmall = RoundedCornerShape(4.dp),
    /** Small rounded corners with 8.dp radius */
    small = RoundedCornerShape(8.dp),
    /** Medium rounded corners with 12.dp radius */
    medium = RoundedCornerShape(12.dp),
    /** Large rounded corners with 16.dp radius */
    large = RoundedCornerShape(16.dp),
    /** Extra large rounded corners with 28.dp radius */
    extraLarge = RoundedCornerShape(28.dp)
)
