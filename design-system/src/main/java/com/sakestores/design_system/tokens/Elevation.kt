package com.sakestores.design_system.tokens

import androidx.compose.ui.unit.dp

/**
 * Defines elevation values used throughout the Sake Stores design system.
 *
 * Elevation values are represented as [dp] units and correspond to
 * different shadow depths for UI elements, helping to establish
 * visual hierarchy and layering.
 *
 * Includes predefined sizes from none (0.dp) to xxxl (24.dp),
 * and semantic aliases for common UI components such as cards,
 * floating action buttons, app bars, and modals.
 */
object SakeElevation {
    val none = 0.dp
    val xs = 1.dp
    val sm = 2.dp
    val md = 4.dp
    val lg = 8.dp
    val xl = 12.dp
    val xxl = 16.dp
    val xxxl = 24.dp

    val card = sm
    val floatingActionButton = lg
    val appBar = md
    val modal = xxl
}
