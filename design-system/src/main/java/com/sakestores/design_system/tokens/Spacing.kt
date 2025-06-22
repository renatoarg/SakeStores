package com.sakestores.design_system.tokens

import androidx.compose.ui.unit.dp

/**
 * Defines standard spacing values used throughout the Sake Stores design system.
 *
 * Spacing values are represented as [dp] units and help maintain
 * consistent padding, margins, and gaps across the UI.
 *
 * Includes predefined sizes from extra small (4.dp) to triple extra large (64.dp),
 * as well as semantic aliases for common layout needs such as card padding,
 * screen padding, item spacing, and section spacing.
 */
object SakeSpacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 16.dp
    val lg = 24.dp
    val xl = 32.dp
    val xxl = 48.dp
    val xxxl = 64.dp

    val cardPadding = md
    val screenPadding = lg
    val itemSpacing = sm
    val sectionSpacing = xl
}
